import java.util.*;

import static java.lang.System.out;

public class Main {
    private static Map<Integer, List<Reserva>> reservasPorQuarto = new HashMap<>();
    private static Map<Integer, Boolean> disponibilidadeQuartos = new HashMap<>();

    public static void main(String[] args) {
        ReservaFacade reservaFacade = new ReservaFacade();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            //For simplificado ,poderia ter feito o uso de um while.
            for (String s : Arrays.asList("Escolha uma ação:", "1. Fazer reserva de quarto", "2. Cancelar reserva", "3. Realizar pagamento", "4. Verificar disponibilidade", "5. Sair")) {
                out.println(s);
            }
            int escolha = scanner.nextInt();

            // Fiz o uso de vários cases como uma estrutura de controle para efetuar as ações do sistema.
            switch (escolha) {
                case 1:
                    out.println("Número do quarto:");
                    int numeroQuarto = scanner.nextInt();
                    out.println("Data de entrada (AAAA-MM-DD):");
                    String dataEntrada = scanner.next();
                    out.println("Data de saída (AAAA-MM-DD):");
                    String dataSaida = scanner.next();
                    out.println("Nome do hóspede:");
                    String nomeHospede = scanner.next();

                    boolean reservaFeita = reservaFacade.fazerReservaQuarto(numeroQuarto, dataEntrada, dataSaida, nomeHospede);

                    //Lógica para verificar se o quarto encontra-se disponível para reserva, se estiver disponível ele armazena
                    // no hashMap utilizando o put, caso não esteja disponível caí no else.
                    if (reservaFeita) {
                        if (verificarDisponibilidade(numeroQuarto, dataEntrada, dataSaida)) {
                            reservasPorQuarto.computeIfAbsent(numeroQuarto, k -> new ArrayList<>())
                                    .add(new Reserva(numeroQuarto, dataEntrada, dataSaida, nomeHospede));
                            disponibilidadeQuartos.put(numeroQuarto, false);
                            out.println("Reserva feita com sucesso!");
                        } else {
                            out.println("Não foi possível fazer a reserva. O quarto já está reservado nesse período.");
                        }
                    } else {
                        out.println("Não foi possível fazer a reserva.");
                    }
                    break;

                case 2:
                    out.println("Número do quarto a ser cancelado:");
                    int numeroQuartoCancelar = scanner.nextInt();
                    boolean reservaCancelada = reservaFacade.cancelarReserva(numeroQuartoCancelar);

                    //Aqui ele faz a exclusão dos dados armazenados no hashmap
                    if (reservaCancelada) {
                        reservasPorQuarto.remove(numeroQuartoCancelar);
                        disponibilidadeQuartos.put(numeroQuartoCancelar, true);
                        out.println("Reserva cancelada com sucesso!");
                    } else {
                        out.println("Não foi possível cancelar a reserva.");
                    }
                    break;
                //Não montei uma regra exata, o usuário só digita o quarto e o valor que irá realizar o pagamento.
                case 3:
                    out.println("Número do quarto para pagamento:");
                    int numeroQuartoPagamento = scanner.nextInt();
                    out.println("Valor do pagamento:");
                    double valorPagamento = scanner.nextDouble();
                    boolean pagamentoRealizado = reservaFacade.realizarPagamento(numeroQuartoPagamento, valorPagamento);

                    if (pagamentoRealizado) {
                        out.println("Pagamento realizado com sucesso! Volte sempre!");
                    } else {
                        out.println("Não foi possível realizar o pagamento.");
                    }
                    break;

                case 4:
                    out.println("Número do quarto para verificar disponibilidade:");
                    int numeroQuartoVerificar = scanner.nextInt();
                    out.println("Data de entrada (AAAA-MM-DD):");
                    String dataEntradaVerificar = scanner.next();
                    out.println("Data de saída (AAAA-MM-DD):");
                    String dataSaidaVerificar = scanner.next();
                    boolean disponivel = verificarDisponibilidade(numeroQuartoVerificar, dataEntradaVerificar, dataSaidaVerificar);

                    //Após o retorno do método "verificarDisponibilidade", segue o bloco if.
                    if (disponivel) {
                        out.println("O quarto está disponível para as datas especificadas.");
                    } else {
                        out.println("O quarto não está disponível para as datas especificadas.");
                    }
                    break;

                case 5:
                    out.println("Encerrando.");
                    scanner.close();
                    System.exit(0);

                default:
                    out.println("Escolha inválida. Tente novamente.");
            }
        }
    }
    //Metódo criado para receber os parametros e verificar se já possui no hashmap os valores armazenados
    private static boolean verificarDisponibilidade(int numeroQuarto, String dataEntrada, String dataSaida) {
        if (disponibilidadeQuartos.getOrDefault(numeroQuarto, true)) return true;

        List<Reserva> reservas = reservasPorQuarto.get(numeroQuarto);
        //Verifica se o periodo passado pelo usuario já é existente, caso sim retorna false e alimenta a variável "disponivel".
        if (reservas != null) {
            for (Reserva reserva : reservas) {
                if (dataSaida.compareTo(reserva.getDataEntrada()) >= 0 && dataEntrada.compareTo(reserva.getDataSaida()) <= 0) {
                    return false;
                }
            }
        }
        return true;
    }
}
