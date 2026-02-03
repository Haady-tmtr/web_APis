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
            options.addOption("del", "delete", false, "Suppression de la personne sélectionnée"); // delete ne prendra pas de param, d'où le false

            CommandLine cmd = parser.parse(options, args);
            try {

                String searchName = cmd.getOptionValue('l');
                String firstName = cmd.getOptionValue('f');
                String age = cmd.getOptionValue('a');


                // System.out.println("DEBUG: Le nom recherché est [" + searchName + "]");
                boolean foundOne = false;

                ///  Suppression :
                boolean deleteMode = cmd.hasOption("del");

                for (int i = 0; i < array.length(); i++) {
                    Person p = Person.fromJSON(array.getJSONObject(i));

                    boolean match = true;


                    if (searchName != null && !p.getLastname().equalsIgnoreCase(searchName)){
                        match  = false;
                    }
                    if (firstName != null && !p.getFirstname().equalsIgnoreCase(firstName)){
                        match  = false;

                        //foundOne  =true;
                    }
                    if (age != null){
                        if (Integer.parseInt(age) != p.getAge()) {
                            //System.out.println(p.getFirstname() + " " + p.getLastname() + " (" + p.getAge() + " ans)");
                            match = false;
                        }


                    }


                    // Sécurité : Si on veut supprimer, il FAUT un nom ET un prénom

                    if (deleteMode && (searchName == null || firstName == null)) {
                        System.out.println("Erreur : Pour supprimer, vous devez préciser un nom (-l) ET un prénom (-f).");
                        return; // On arrête tout
                    }

                    if (match){

                        if (deleteMode){
                            HttpRequest deletedRequest = HttpRequest.newBuilder()
                                    .uri(URI.create("http://localhost:8000" + p.getUri()))
                                    .DELETE()
                                    .build();

                            HttpResponse<String> responseDelete =
                                    httpClient.send(deletedRequest, BodyHandlers.ofString());

                            if (responseDelete.statusCode() == 200 || responseDelete.statusCode() == 204){
                                System.out.println("Suppression de " + p.getFirstname() +" " + p.getLastname() + ", de la base.");
                                foundOne = true;
                            }
                            else {
                                System.out.println("Erreur lors de la suppression de " + p.getFirstname() +" " + p.getLastname() + ", d'ID: " + p.getId());
                            }
                        }

                        else {
                            System.out.println(p.getFirstname() + " " + p.getLastname() + " (" + p.getAge() + " ans)");
                            foundOne = true;
                        }
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

            try { // Delete









            } catch (Exception e) {
                System.out.println("Erreur de la requête de suppression");
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

