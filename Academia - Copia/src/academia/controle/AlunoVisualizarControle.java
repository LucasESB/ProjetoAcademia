package academia.controle;

import academia.dao.AlunosDao;
import academia.dao.RecebimentosDao;
import academia.entidades.Alunos;
import academia.entidades.Recebimentos;
import academia.utilitarios.DataHora;
import academia.utilitarios.Textos;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class AlunoVisualizarControle implements Initializable {

    @FXML
    private TableColumn<Recebimentos, String> col_dataVencimento;

    @FXML
    private TableColumn<Recebimentos, String> col_situacao;

    @FXML
    private TableColumn<Recebimentos, String> col_vDesconto;

    @FXML
    private TableColumn<Recebimentos, String> col_vMensalidade;

    @FXML
    private TableColumn<Recebimentos, String> col_vTotal;

    @FXML
    private TableColumn<Recebimentos, String> dat_Pagamento;

    @FXML
    private TableView<Recebimentos> tab_recebimentos;

    @FXML
    private TextField tex_cpf;

    @FXML
    private TextField tex_dataCadastro;

    @FXML
    private TextField tex_dataNascimento;

    @FXML
    private TextField tex_diaPrePagamento;

    @FXML
    private TextField tex_email;

    @FXML
    private TextField tex_nome;

    @FXML
    private TextArea tex_observacoes;

    @FXML
    private TextField tex_sexo;

    @FXML
    private TextField tex_telefone;

    @FXML
    private TextField tex_whatsapp;

    /**
     * Instancia da janela
     */
    private static Stage janela;

    /**
     * Instancia da classe
     */
    private static AlunoVisualizarControle instancia;

    /**
     * Objeto de conexão com a tabela recebimentos
     */
    private RecebimentosDao recebimentosDao = RecebimentosDao.getInstance();

    private AlunosDao alunosDao = AlunosDao.getInstance();

    private Alunos aluno;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_dataVencimento.setCellValueFactory(new PropertyValueFactory<Recebimentos, String>("dataVencimentoFormatado"));
        col_situacao.setCellValueFactory(new PropertyValueFactory<Recebimentos, String>("situacao"));
        col_vDesconto.setCellValueFactory(new PropertyValueFactory<Recebimentos, String>("vDescontoFormatado"));
        col_vMensalidade.setCellValueFactory(new PropertyValueFactory<Recebimentos, String>("vRecebimentoFormatado"));
        col_vTotal.setCellValueFactory(new PropertyValueFactory<Recebimentos, String>("vTotalFormatado"));
        dat_Pagamento.setCellValueFactory(new PropertyValueFactory<Recebimentos, String>("dataPagamentoFormatado"));
    }

    public AlunoVisualizarControle() throws Exception {
    }

    private void configuracoes() {
        new ThreadBuscaHistoricoRecebimentos().start();
        setDados();
    }

    private void setDados() {
        tex_nome.setText(aluno.getNome());
        tex_dataCadastro.setText(DataHora.formatarData(aluno.getDataCadastro(), "dd/MM/yyyy"));
        tex_diaPrePagamento.setText(Integer.toString(aluno.getDiaPrePagamento()));
        tex_cpf.setText(Textos.formatarCPF(aluno.getCpf()));
        tex_dataNascimento.setText(DataHora.formatarData(aluno.getDataNascimento(), "dd/MM/yyyy"));
        tex_telefone.setText(Textos.formatarTelefone(aluno.getTelefone()));
        tex_whatsapp.setText(Textos.formatarTelefone(aluno.getWhatsapp()));
        tex_sexo.setText(aluno.getSexoDescricao());
        tex_email.setText(aluno.getEmail());
        tex_observacoes.setText(aluno.getObservacao());
    }

    private class ThreadBuscaHistoricoRecebimentos extends Thread {
        @Override
        public void run() {
            try {
                List<Recebimentos> listRecebimentos = recebimentosDao.getRecebimentos("WHERE aluno_id = " + aluno.getId() + "  ORDER BY dataVencimento DESC");

                if (listRecebimentos.isEmpty()) return;

                Recebimentos rec = listRecebimentos.get(0);
                Date dataUltimoRecebimento = DataHora.stringParseDate(DataHora.formatarData(rec.getDataVencimento(), "dd/MM/yyyy"), "dd/MM/yyyy");
                Date dataRecebimentoDoMes = DataHora.stringParseDate(DataHora.formatarData(aluno.getDataPrePagamento(new Date()), "dd/MM/yyyy"), "dd/MM/yyyy");

                if (dataUltimoRecebimento.before(dataRecebimentoDoMes)) {
                    long diferencaMeses = DataHora.getDiferencaEntreData(dataUltimoRecebimento, dataRecebimentoDoMes, DataHora.MES);

                    double vMensalidade = alunosDao.getvMensalidade(aluno);

                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dataUltimoRecebimento);

                    for (int i = 0; i < diferencaMeses; i++) {
                        cal.add(Calendar.MONTH, 1);

                        Recebimentos recebimentos = new Recebimentos();
                        recebimentos.setDataVencimento(cal.getTime());
                        recebimentos.setvRecebimento(vMensalidade);

                        listRecebimentos.add(0, recebimentos);
                    }
                }

                tab_recebimentos.setItems(FXCollections.observableList(listRecebimentos));

            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        }
    }

    /**
     * Metodo responsavel por abrir a janela
     */
    public static void abrirTela(Alunos aluno) throws IOException, URISyntaxException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AlunoVisualizarControle.class.getResource("/academia/telas/AlunoVisualizar.fxml"));
        AnchorPane page = (AnchorPane) loader.load();

        janela = new Stage();
        janela.setTitle("Dados do Aluno");

        Scene scene = new Scene(page);
        janela.setScene(scene);

        janela.getIcons().add(new Image(AlunoVisualizarControle.class.getResource("/academia/assets/icons/upfitsistema.png").toURI().toString()));
        janela.initModality(Modality.APPLICATION_MODAL);
        janela.resizableProperty().setValue(false);

        instancia = loader.getController();
        instancia.aluno = aluno;
        instancia.configuracoes();

        janela.showAndWait();
    }
}
