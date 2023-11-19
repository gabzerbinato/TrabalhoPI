import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Localizacao {
    String logradouro, bairro, cidade, estado, cep;
    int numero;

    public Localizacao(String cep, int numero) {
        this.cep = cep;
        this.numero = numero;
        String url = "https://viacep.com.br/ws/" + cep + "/json/";
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("conexão com sucesso");
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                int linha = 0;
                while ((inputLine = in.readLine()) != null) {
                    switch (linha) {
                        case 2:
                            this.logradouro = (extractValue(inputLine));
                            break;
                        case 4:
                            this.bairro = (extractValue(inputLine));
                            break;
                        case 5:
                            this.cidade = (extractValue(inputLine));
                        case 6:
                            this.estado = (extractValue(inputLine));
                        default:
                            break;
                    }
                    linha++;
                }
                in.close();
            } else {
                System.out.println("Erro ao fazer a requisição. Código de resposta: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static String extractValue(String jsonString) {
        jsonString = jsonString.replaceAll("[{}\"]", "");
        int indiceDoisPontos = jsonString.indexOf(":");
        jsonString = jsonString.substring(indiceDoisPontos + 1, jsonString.length() - 1).trim();
        return jsonString;
    }

    @Override
    public String toString() {
        return "" + logradouro + " " + numero + " " + bairro + " " + cidade + " " + estado;
    }
}
