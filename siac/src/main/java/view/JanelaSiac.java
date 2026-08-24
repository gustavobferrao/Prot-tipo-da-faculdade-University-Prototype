package view;

import conexao.JPAUtil;
import dao.ClienteDAO;
import dao.SolicitacaoCreditoDAO;
import modelo.entidades.Cliente;
import modelo.entidades.SolicitacaoCredito;
import service.CreditoService;
import service.MotorRegras;

import javax.persistence.EntityManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

public class JanelaSiac extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnCadastrar;

    // Definição da Paleta de Cores
    private final Color COR_FUNDO = new Color(33, 43, 54);       // Azul escuro /cinza moderno
    private final Color COR_CAMPO = new Color(45, 55, 72);        // Fundo dos campos (um pouco mais claro)
    private final Color COR_TEXTO = new Color(255, 255, 255);    // Branco
    private final Color COR_ACCENT = new Color(66, 153, 225);    // Azul destaque para o botão principal
    private final Color COR_BOTAO_SEC = new Color(74, 85, 104);  // Cinza para botão secundário

    // Fontes
    private final Font FONTE_LABEL = new Font("Segoe UI", Font.BOLD, 15);
    private final Font FONTE_CAMPO = new Font("Segoe UI", Font.PLAIN, 14);

    public JanelaSiac(){
        // configurações básicas da janela
        setTitle("Sistema de Crédito Inteligente");
        setSize(480,350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); //centraliza a tela

        // Painel único organizado em 6 linhas e 1 coluna (com espaçamento de 8px entre as linhas)
        JPanel painel = new JPanel(new GridLayout(6, 1, 0, 8));
        painel.setBackground(COR_FUNDO);
        painel.setBorder(new EmptyBorder(25, 35, 25, 35)); // Margem nas bordas da tela
        setContentPane(painel);


        // inicializando os componentes
        JLabel lblUsuario = new JLabel("  Usuário / CPF:");
        lblUsuario.setFont(FONTE_LABEL);
        lblUsuario.setForeground(COR_TEXTO);

        txtUsuario = new JTextField();
        txtUsuario.setFont(FONTE_CAMPO);
        txtUsuario.setBackground(COR_CAMPO);
        txtUsuario.setForeground(COR_TEXTO);
        txtUsuario.setCaretColor(COR_TEXTO);
        txtUsuario.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8)); // Margem interna do texto


        JLabel lblSenha = new JLabel("  Senha:");
        lblSenha.setFont(FONTE_LABEL);
        lblSenha.setForeground(COR_TEXTO);

        txtSenha = new JPasswordField();
        txtSenha.setFont(FONTE_CAMPO);
        txtSenha.setBackground(COR_CAMPO);
        txtSenha.setForeground(COR_TEXTO);
        txtSenha.setCaretColor(COR_TEXTO);
        txtSenha.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));

        btnEntrar = new JButton("Entrar:");
        configurarBotao(btnEntrar, COR_ACCENT);

        btnCadastrar = new JButton("Criar conta:");
        configurarBotao(btnCadastrar, COR_BOTAO_SEC);

        // adicionando os componentes ao JFrame na ordem de aparição (cima para baixo)
        add(lblUsuario);
        add(txtUsuario);
        add(lblSenha);
        add(txtSenha);
        add(btnEntrar);
        add(btnCadastrar);

        // ação do botão entrar
        btnEntrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cpfDigitado = txtUsuario.getText();
                String senha = new String(txtSenha.getPassword());

               // Abrir o entity manager para consultar o login
                EntityManager em = JPAUtil.getEntityManager();
                ClienteDAO clienteDAO = new ClienteDAO(em);

                try {
                    Optional<Cliente> clienteLogado = clienteDAO.realizarLogin(cpfDigitado, senha);

                    if(clienteLogado.isPresent()){
                        JOptionPane.showMessageDialog(null, "Login efetuado com sucesso! " +
                                "Redirecionando para o console...");
                        //oculta e fecha a tela gráfica
                        dispose();
                        em.close();

                        chamarFluxoEmprestimo(clienteLogado.get());
                    } else {
                        JOptionPane.showMessageDialog(null,"CPF ou Senha inválidos!",
                                "Erro de autenticação!", JOptionPane.ERROR_MESSAGE);
                        em.close();
                    }
                } catch (Exception er){
                    JOptionPane.showMessageDialog(null, "Erro ao conectar com banco:" +
                            er.getMessage());
                    if ((em.isOpen())) em.close();
                }


            }
        });

        // ação do botão cadastrar (abre a tela de cadastro)
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: a decidir o que fazer
            }
        });
    }

    // Method rápido para tirar as bordas padrão e aplicar as cores nos botões
    private void configurarBotao(JButton botao, Color corFundo) {
        botao.setFont(FONTE_LABEL);
        botao.setBackground(corFundo);
        botao.setForeground(COR_TEXTO);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Efeito da mãozinha
    }

    private void chamarFluxoEmprestimo(Cliente clienteLogado) {
        // Como o cliente já fez login pela tela gráfica, o Scanner não vai pedir os dados cadastrais de novo!
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("\n=============================================");
            System.out.println("  BEM-VINDO AO TERMINAL DE EMPRÉSTIMOS ");
            System.out.println("=============================================");
            System.out.println("Cliente Autenticado: " + clienteLogado.getNome());
            System.out.println("Score Atual: " + clienteLogado.getScore());
            System.out.println("---------------------------------------------");

            System.out.print("Digite o valor do crédito solicitado: R$ ");
            BigDecimal valorSolicitado = new BigDecimal(scanner.nextLine().trim());

            // Cria o ambiente limpo para processar a proposta
            EntityManager em = JPAUtil.getEntityManager();
            ClienteDAO clienteDAO = new ClienteDAO(em);
            SolicitacaoCreditoDAO solicitacaoDAO = new SolicitacaoCreditoDAO(em);
            MotorRegras motorRegras = new MotorRegras();
            CreditoService creditoService = new CreditoService(clienteDAO, solicitacaoDAO, em, motorRegras);

            System.out.println("\nProcessando proposta no motor de regras...");

            // Executa o fluxo de crédito passando os dados do cliente logado
            SolicitacaoCredito resultado = creditoService.processarFluxoCredito(
                    clienteLogado.getNome(),
                    clienteLogado.getCpf(),
                    clienteLogado.getSenha(),
                    clienteLogado.getRenda(),
                    clienteLogado.getScore(),
                    valorSolicitado
            );

            System.out.println("Digite o seu CPF para confimar: ");
            String validarCpf = scanner.nextLine();
            System.out.println("Digite sua SENHA para confirmar: ");
            String validarSenha = scanner.nextLine();

            if(validarCpf.equals(clienteLogado.getCpf()) && validarSenha.equals(clienteLogado.getSenha())){
                System.out.println("\n====== RESULTADO ======");
                System.out.println("Status Final: " + resultado.getStatus());
                if ("APROVADO".equals(resultado.getStatus())) {
                    System.out.println("Valor aprovado: R$ "+resultado.getValorSolicitado());
                    System.out.println("Juros Calculados: R$ " + resultado.getJuros());
                    System.out.println("Total a Pagar: R$ " + valorSolicitado.add(resultado.getJuros()));
                }
                System.out.println("=======================");
            } else {
                System.err.println("CPF ou SENHA inválidos, tente novamente outra hora...");
            }


            em.close();

        } catch (Exception e) {
            System.err.println("Erro no processamento do console: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
