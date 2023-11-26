import controller.IncidentController;
import controller.PersonController;
import controller.ProblemController;
import controller.ServiceController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

public class ServiceManagement {

    public static EntityManager getEntityManager(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA_PU");
        return factory.createEntityManager();
    }

    public static void main(String[] args) {

        EntityManager em = ServiceManagement.getEntityManager();
        Scanner scanner = new Scanner(System.in);

        PersonController pc = new PersonController();
        pc.getPr().setEm(em);
        ServiceController sc = new ServiceController();
        sc.getSr().setEm(em);
        IncidentController ic = new IncidentController();
        ic.getIr().setEm(em);
        ProblemController prc = new ProblemController();
        prc.getPrR().setEm(em);

        System.out.println("Sistema de gestion de incidentes");
        String opc;
        do {
            System.out.println("-".repeat(30));
            System.out.println("1. Alta cliente");
            System.out.println("2. Alta tecnico");
            System.out.println("3. Ver clientes");
            System.out.println("4. Ver tecnicos disponibles");
            System.out.println("5. Agregar Servicios/Especialidades");
            System.out.println("6. Ver Servicios/Especialidades");
            System.out.println("7. Agregar servicio/especialidad a cliente/tecnico");
            System.out.println("8. Dar de alta problema");
            System.out.println("9. Reportar incidente");
            System.out.println("10. Ver todos los incidentes");
            System.out.println("11. Dar incidente por finalizado");
            System.out.println("0. Salir");
            System.out.println("-".repeat(30));
            System.out.println("Ingrese una opcion: ");
            opc = scanner.nextLine();
            switch (opc) {
                case "1":
                    pc.addClient(scanner);
                    break;
                case "2":
                    pc.addTechinician(scanner);
                    break;
                case "3":
                    pc.showClients();
                    break;
                case "4":
                    pc.showTechnicians();
                    break;
                case "5":
                    sc.newService(scanner);
                    break;
                case "6":
                    sc.showServices();
                    break;
                case "7":
                    pc.addService(scanner, sc);
                    break;
                case "8":
                    prc.addProblem(scanner, sc);
                    break;
                case "9":
                    ic.reportIncident(scanner, pc, prc, sc);
                    break;
                case "10":
                    ic.viewAllIncidents();
                    break;
                case "11":
                    ic.resolveIncident(scanner);
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Debe ingresar una opcion, intente nuevamente");
            }
        } while (!opc.equals("0"));


    }
}
