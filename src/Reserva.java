public class Reserva {
    private int numeroQuarto;
    private String dataEntrada;
    private String dataSaida;
    private String nomeHospede;

    public Reserva(int numeroQuarto, String dataEntrada, String dataSaida, String nomeHospede) {
        this.numeroQuarto = numeroQuarto;
        this.dataEntrada = dataEntrada;
        this.dataSaida = dataSaida;
        this.nomeHospede = nomeHospede;
    }

    public int getNumeroQuarto() {
        return numeroQuarto;
    }

    public String getDataEntrada() {
        return dataEntrada;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public String getNomeHospede() {
        return nomeHospede;
    }
}
