package controller;

import lombok.Getter;
import lombok.Setter;
import model.Client;
import model.Problem;
import model.Service;
import repository.ProblemRepository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Scanner;

@Getter
public class ProblemController {

    @Setter
    private ProblemRepository prR;

    public ProblemController() {
        this.prR = new ProblemRepository();
    }

    public Problem getProblemById(Scanner scanner, Client client, ServiceController sc) throws NoResultException {
        getProblemsOfClient(client);
        System.out.println("Ingrese numero de ID - 0 para generar un nuevo problema");

        long id = Long.parseLong(scanner.nextLine());
        if (id == 0L){
            return this.addProblem(scanner, sc);
        }
        return prR.getProblemByServicesById(client.getServices(), id);
    }

    public void getProblemsOfClient(Client client){
        List<?> problemList = prR.getProblemByServices(client.getServices());
        problemList.forEach(System.out::println);
    }

    public Problem addProblem (Scanner scanner, ServiceController sc) {
        System.out.println("Alta de problemas");
        System.out.println("Ingrese descripcion del problema");
        String description = scanner.nextLine();
        sc.showServices();
        System.out.println("Seleccione servicio a agregar:");
        long id = Long.parseLong(scanner.nextLine());
        Service service = sc.getServiceById(id);
        System.out.println("Ingrese tiempo de resolucion en horas");
        double resTime = Double.parseDouble(scanner.nextLine());
        Problem problem = new Problem();
        problem.setDescription(description);
        problem.setService(service);
        problem.setResolutionTime(resTime);
        prR.addProblem(problem);
        return problem;
    }
}
