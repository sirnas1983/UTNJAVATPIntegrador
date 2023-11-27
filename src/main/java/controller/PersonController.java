package controller;

import lombok.Getter;
import lombok.Setter;
import model.*;
import repository.PersonRepository;

import javax.persistence.NoResultException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class PersonController {

    @Getter
    @Setter
    private PersonRepository pr;

    public PersonController(){
        this.pr = new PersonRepository();
    }

    public void addClient(Scanner scanner){
        System.out.println("Ingrese nombre:");
        String name = scanner.nextLine();
        System.out.println("Ingrese direccion:");
        String adress = scanner.nextLine();
        boolean passed = false;
        DocTipe docTipe = DocTipe.DNI;
        do{
        try {
            System.out.println("Ingrese tipo de documento:");
            Arrays.stream(DocTipe.values()).toList().forEach(System.out::println);
            docTipe = DocTipe.valueOf(scanner.nextLine().toUpperCase());
            passed = true;
        } catch (Exception e){
            System.out.println("Tipo documento invalido, intente nuevamente.");
        }
        } while (!passed);
        System.out.println("Ingrese numero de documento");
        String idNum = scanner.nextLine();
        System.out.println("Ingrese email:");
        String email = scanner.nextLine();
        Person client = new Client();
        client.setName(name);
        client.setAdress(adress);
        client.setEmail(email);
        client.setDocTipe(docTipe);
        client.setIdNum(idNum);
        pr.addPerson(client);
    }

    public void showClients(){
        System.out.println("Clientes:");
        pr.getClients().forEach(System.out::println);
        System.out.println();
    }

    public void showTechnicians(){
        System.out.println("Tecnicos:");
        pr.getTechnicians().forEach(System.out::println);
        System.out.println();
    }

    public void showTechniciansBySpeciality(Service service) throws NoResultException{
        System.out.println("Tecnicos:");
        pr.getTechniciansBySpeciality(service).forEach(System.out::println);
        System.out.println();
    }

    public void showPersons(){
        System.out.println("Listado de clientes/tecnicos:");
        pr.getPersons().forEach(System.out::println);
        System.out.println();
    }

    public Person selectById(Scanner scanner){
        this.showPersons();
        System.out.println("Ingrese id de persona con la cual desea operar");
        long id = Long.parseLong(scanner.nextLine());
        Person person = pr.getPersonById(id);
        if (person != null){
            System.out.println("Has seleccionado " + person);
        }
        return person;
    }

    public Client selectClientById(Scanner scanner){
        this.showClients();
        System.out.println("Ingrese id de cliente");
        long id = Long.parseLong(scanner.nextLine());
        Client client = pr.getClientById(id);
        if (client != null) {
            System.out.println("Has seleccionado " + client);
        }
        return client;
    }

    public void addService(Scanner scanner, ServiceController sc){
        try {
            Person person = this.selectById(scanner);
            if (person!=null){
                boolean cont;
                Service service;
                do {
                    sc.showServices();
                    System.out.println("Seleccione servicio a agregar:");
                    long id = Long.parseLong(scanner.nextLine());
                    service = sc.getServiceById(id);
                    if (person.getClass().equals(Technician.class)) {
                        ((Technician) person).getSpecialities().add(service);
                    } else if (person.getClass().equals(Client.class)) {
                        ((Client) person).getServices().add(service);
                    }
                    System.out.println("¿Desea agregar otro servicio? s/n");
                    cont = scanner.nextLine().equalsIgnoreCase("s");
                } while (cont);
                pr.updatePerson(person);
                System.out.println("Servicio/s agregado/s correctamente");
            }else{
                System.out.println("No hay coincidencias para su busqueda");
            }
        } catch (Exception e) {
            System.out.println("Se produjo un error, intente nuevamente");
        }
    }

    public void addTechinician(Scanner scanner){
        System.out.println("Ingrese nombre:");
        String name = scanner.nextLine();
        System.out.println("Ingrese direccion:");
        String adress = scanner.nextLine();
        boolean passed = false;
        DocTipe docTipe = DocTipe.DNI;
        do{
            try {
                System.out.println("Ingrese tipo de documento:");
                Arrays.stream(DocTipe.values()).toList().forEach(System.out::println);
                docTipe = DocTipe.valueOf(scanner.nextLine().toUpperCase());
                passed = true;
            } catch (Exception e){
                System.out.println("Tipo documento invalido, intente nuevamente.");
            }
        } while (!passed);
        System.out.println("Ingrese numero de documento");
        String idNum = scanner.nextLine();
        System.out.println("Ingrese email:");
        String email = scanner.nextLine();
        System.out.println("Esta disponible? s/n");
        Boolean isAvailable = scanner.nextLine().equalsIgnoreCase("s");
        System.out.println("Ingrese numero de Whatsapp si posee");
        String whatsapp = scanner.nextLine();
        System.out.println("Medio preferido de comunicacion? w(hatsapp)/e(mail)");
        FavoriteCom favoriteCom = scanner.nextLine().equalsIgnoreCase("w") ? FavoriteCom.WHATSAPP : FavoriteCom.EMAIL;
        Technician technician = new Technician();
        technician.setName(name);
        technician.setAdress(adress);
        technician.setEmail(email);
        technician.setDocTipe(docTipe);
        technician.setIdNum(idNum);
        technician.setIsAvailable(isAvailable);
        technician.setWhatsapp(whatsapp);
        technician.setFavoriteCom(favoriteCom);
        pr.addPerson(technician);
    }

    public Technician selectTechnicianById(Problem problem, Scanner scanner){
        List<Technician> technicians = pr.getTechniciansBySpeciality(problem.getService());
        if(technicians.isEmpty()){
            System.out.println("No hay tecnicos disponibles con esa especialidad");
            return null;
        } else {
            try {
                this.showTechniciansBySpeciality(problem.getService());
                System.out.println("Ingrese id de tecnico");
                long id = Long.parseLong(scanner.nextLine());
                Technician technician = pr.selectTechnicianBySpecialityAndId(problem.getService(), id);
                System.out.println("Has seleccionado " + technician);
                return technician;
            } catch (NoResultException e){
                System.out.println("No hay tecnicos que se ajusten a tu busqueda");
                return null;
            }
        }
    }



}
