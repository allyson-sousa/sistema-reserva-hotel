public class ReservaFacade {
    private Quarto quarto;
    private Pagamento pagamento;

    public ReservaFacade() {
        this.quarto = new Quarto();
        this.pagamento = new Pagamento();
    }

    public boolean fazerReservaQuarto(int numeroQuarto, String dataEntrada, String dataSaida, String nomeHospede) {
        boolean reserva = quarto.reservar(numeroQuarto, dataEntrada, dataSaida, nomeHospede);

        if (reserva) {
            double valor = calcularValorDaReserva(dataEntrada, dataSaida);
            boolean pagamentoRealizado = pagamento.realizarPagamento(numeroQuarto, valor);
            return pagamentoRealizado;
        }

        return false;
    }

    public boolean cancelarReserva(int numeroQuarto) {
        return quarto.cancelar(numeroQuarto);
    }

    public boolean realizarPagamento(int numeroQuarto, double valor) {
        return pagamento.realizarPagamento(numeroQuarto, valor);
    }
    //Optei por criar o método direto na classe main.
    public boolean verificarDisponibilidade(int numeroQuarto, String dataEntrada, String dataSaida) {
        return quarto.verificarDisponibilidade(numeroQuarto, dataEntrada, dataSaida);
    }

    private double calcularValorDaReserva(String dataEntrada, String dataSaida) {
        return 100.0;
    }
}
