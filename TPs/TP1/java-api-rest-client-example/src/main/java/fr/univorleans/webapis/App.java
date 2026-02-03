package fr.univorleans.webapis;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.apache.commons.cli.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class App {
    public static void main(String[] args) {
        try {
            HttpClient httpClient = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8000/api/people"))
                    .GET()
                    .build();
            HttpResponse<String> response =
                httpClient.send(request, BodyHandlers.ofString());
            //System.out.println("I'm here ");
            if (response.statusCode() != 200)

                throw(new Exception());

            JSONObject json = new JSONObject(response.body());
            JSONArray array = json.getJSONArray("member");
            /////////////////////////////////////////////////////////////////////////// Recupération des personnes
            // Apache CLI

            Options options = new Options();
            CommandLineParser parser = new DefaultParser();
            options.addOption("l", "lastname", true, "Rechercher par nom de famille");
            options.addOption("f", "firstname", true, "Filtrer par prénom");
            options.addOption("a", "age", true, "Filtrer par âge");


            try {
                CommandLine cmd = parser.parse(options, args);

                String searchName = cmd.getOptionValue('l');
                String fisrtName = cmd.getOptionValue('f');
                String age = cmd.getOptionValue('a');


                // System.out.println("DEBUG: Le nom recherché est [" + searchName + "]");
                boolean foundOne = false;

                for (int i = 0; i < array.length(); i++) {
                    Person p = Person.fromJSON(array.getJSONObject(i));


                    // CAS 1 : On affiche TOUT (si searchName est nul, on n'a pas filtré)
                    // CAS 2 : On affiche si le nom correspond exactement
                    if (searchName == null || p.getLastname().equalsIgnoreCase(searchName)){
                        //System.out.println(p.getFirstname() + " " + p.getLastname() + " (" + p.getAge() + " ans). Son numéro est : " + p.getPhone_number());
                        System.out.println(p.getFirstname() + " " + p.getLastname() + " (" + p.getAge() + " ans)");

                        foundOne  =true;
                    }
                }

                if (!foundOne){
                    if (searchName != null) {
                        System.out.println("Aucune personne trouvée pour le nom : " + searchName);
                    } else {
                        System.out.println("La base est vide.");
                    }
                }


            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        } catch (Exception e) {
            System.out.println("The server did not respond OK.");
            e.printStackTrace();
        }
















    }
}


            /*for (int i = 0; i < array.length(); i++) {
                Person person = Person.fromJSON(array.getJSONObject(i));
                System.out.println(person.getLastname()
                        + ", " + person.getFirstname()
                        + ", age: " + person.getAge());

                int newAge = person.getAge() + 1;

                // 2. Création de l'objet de mise à jour
                JSONObject updatedData = new JSONObject();
                updatedData.put("age", newAge);

                // 3. Correction de l'URL (@id contient déjà le chemin)
                String personUrl = "http://localhost:8000" + person.getUri();

                System.out.println("URL exact ? " + personUrl);

                // 4. Requête PATCH
                HttpRequest updateRequest = HttpRequest.newBuilder()
                        .uri(URI.create(personUrl))
                        .header("Content-Type", "application/merge-patch+json")
                        .method("PATCH", HttpRequest.BodyPublishers.ofString(updatedData.toString()))
                        .build();

                // 5. Envoi
                HttpResponse<String> updateResponse = httpClient.send(updateRequest, BodyHandlers.ofString());

                if (updateResponse.statusCode() == 200 || updateResponse.statusCode() == 204) {
                    System.out.println(person.getFirstname() + " a maintenant " + newAge + " ans.");
                } else {
                    System.out.println("Erreur " + updateResponse.statusCode() + " pour " + person.getFirstname());
                }



            }*/

