package controller;

import lombok.Getter;
import lombok.Setter;
import model.*;
import repository.IncidentRepository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Scanner;

@Getter
public class IncidentController {

    @Setter
    private IncidentRepository ir;

    public IncidentController() {
        this.ir = new IncidentRepository();
    }

    public void reportIncident(Scanner scanner, PersonController pC, ProblemController prC, ServiceController sc){
        System.out.println("REPORTAR INCIDENTE");
        System.out.println("-".repeat(30));
        Client client = pC.selectClientById(scanner);
        if(client == null || client.getServices().isEmpty()){
            if (client == null){
                System.out.println("No se encontraron clientes con ese ID");
            } else {
                System.out.println("Cliente no tiene Servicios contratados");
            }
        } else {
            try {
                Problem problem = prC.getProblemById(scanner, client, sc);
                if (problem == null) {
                    System.out.println("No se encontraron problemas con ese ID");
                } else {
                    Technician technician = pC.selectTechnicianById(problem, scanner);
                    if (technician != null) {
                        Incident incident = new Incident();
                        incident.setClient(client);
                        incident.setProblem(problem);
                        incident.setTechnician(technician);
                        incident.setIsResolved(false);
                        Double resTime;
                        System.out.println("¿Desea modificar el tiempo de resolucion? s/n");
                        if (scanner.nextLine().equalsIgnoreCase("s")) {
                            resTime = Double.parseDouble(scanner.nextLine());
                        } else {
                            resTime = problem.getResolutionTime();
                        }
                        incident.setResolutionTime(resTime);
                        ir.addIncident(incident);
                        // TODO: Agregar metodo para dar aviso al tecnico una vez se registra el incidente
                        System.out.println("Se le aviso al tecnico por " + technician.getFavoriteCom().toString().toLowerCase());
                    }
                }
            }catch (NoResultException re){
                System.out.println("No existen problemas con ese ID");
            }
        }
    }

    public void viewAllIncidents() {
        System.out.println("INCIDENTES:");
        List<Incident> incidents = ir.getIncidents();
        incidents.forEach(System.out::println);
    }

    public void resolveIncident(Scanner scanner) {
        this.viewIncidentsInCourse();
        System.out.println("Ingrese ID del incidente a cerrar:");
        Long id =Long.parseLong(scanner.nextLine());
        Incident incident = ir.getUnresolvedIncidentById(id);
        if (incident == null){
            System.out.println("No hay coincidencias");
        } else {
            incident.setIsResolved(true);
            ir.updateIncident(incident);
            System.out.println("Incidente actualizado correctamente");
        }
    }

    private void viewIncidentsInCourse() {
        System.out.println("Incidentes no resueltos:");
        List<Incident> incidents = ir.getUnresolvedIncidents();
        incidents.forEach(System.out::println);
    }

    public void viewResolvedIncidents() {
        System.out.println("Incidentes resueltos:");
        List<Incident> incidents = ir.getResolvedIncidents();
        incidents.forEach(System.out::println);

    }
}
