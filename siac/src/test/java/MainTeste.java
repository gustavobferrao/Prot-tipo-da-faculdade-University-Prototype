import conexao.JPAUtil;
import dao.ClienteDAO;
import dao.SolicitacaoCreditoDAO;
import modelo.entidades.SolicitacaoCredito;
import service.CreditoService;
import service.MotorRegras;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Scanner;

//public class MainTeste {
//    public static void main(String[] args) {
//
//        EntityManager em = JPAUtil.getEntityManager();
//
//        ClienteDAO clienteDAO = new ClienteDAO(em);
//        SolicitacaoCreditoDAO solicitacaoCreditoDAO = new SolicitacaoCreditoDAO(em);
//
//        MotorRegras motorRegras = new MotorRegras();
//
//        CreditoService creditoService = new CreditoService(clienteDAO, solicitacaoCreditoDAO, em, motorRegras);
//
//        try(Scanner scanner = new Scanner(System.in)){
//
//            System.out.println("=============================================");
//            System.out.println("  SISTEMA INTELIGENTE DE APROVAÇÃO DE CRÉDITO ");
//            System.out.println("=============================================");
//
//            boolean exectuarApp = true;
//
////            while (exectuarApp){
//                System.out.println("\n--- TESTE DE SIMULAÇÃO DE CRÉDITO ---");
//
//                System.out.println("Digite o CPF do cliente (somente números): ");
//                String cpf = scanner.nextLine().trim();
//
//                System.out.println("Digite o nome completo do cliente: ");
//                String nome = scanner.nextLine().trim();
//
//                System.out.println("Digite a renda mensal (Exemplo: 3500.00): ");
//                BigDecimal renda = new BigDecimal(scanner.nextLine().trim());
//
//                System.out.println("Digite o score do cliente (0 a 1000): ");
//                int score = Integer.parseInt(scanner.nextLine().trim());
//
//                System.out.println("Digite o valor do crédito solicitado: ");
//                BigDecimal valorSolicitado = new BigDecimal(scanner.nextLine().trim());
//
//                System.out.println("\nEnviando dados para o processamento unificado...");
//                System.out.println("---------------------------------------------");
//
//                SolicitacaoCredito solicitacaoCredito =
//                        creditoService.processarFluxoCredito(cpf, nome, senha, renda, score, valorSolicitado);
//
//                System.out.println("\n====== RESULTADO DA ANÁLISE DE CRÉDITO ======");
//                System.out.println("Status Final: " + solicitacaoCredito.getStatus());
//
//                if("APROVADO".equals(solicitacaoCredito.getStatus())){
//                    System.out.println("Taxa de Juros Calculada: R$ "+solicitacaoCredito.getJuros());
//                    BigDecimal totalComJuros = valorSolicitado.add(solicitacaoCredito.getJuros());
//                    System.out.println("Montante Final Devido: R$ "+totalComJuros);
//                } else if ("NEGADO".equals(solicitacaoCredito.getStatus())) {
//                    System.out.println("Motivo: Perfil de risco fora das diretrizes (Renda Baixa ou Score Insuficiente)");
//                } else {
//                    System.out.println("Motivo: Dados limitantes. Encaminhado para a Mesa de Crédito Manual para Análise.");
//                }
//
//                System.out.println("ID técnico do cliente no banco: "+ solicitacaoCredito.getCliente().getId());
//                System.out.println("=============================================");
//
////                System.out.println("\nDeseja realizar uma nova análise? (S/N): ");
////                String resposta = scanner.nextLine().trim();
////
////                if(resposta.equalsIgnoreCase("N")){
////                    exectuarApp = false;
////                }
////            }
//
//            System.out.println("\nEncerrando o terminal. Sistema finalizado.");
//
//        } catch (Exception e){
//            System.err.println("\nERRO Falha na operação do console: "+e.getMessage());
//            e.printStackTrace();
//
//        } finally {
//            if (em != null && em.isOpen()){
//                em.close();
//            }
//
//            JPAUtil.getEntityManager().close();
//            System.out.println("Conexões com a base de dados deslocadas com segurança.");
//        }
//    }
//}
