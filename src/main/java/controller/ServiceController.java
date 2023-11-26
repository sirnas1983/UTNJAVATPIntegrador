package controller;

import lombok.Getter;
import lombok.Setter;
import model.OpSys;
import model.Service;
import model.Software;
import repository.ServiceRepository;

import java.util.Arrays;
import java.util.Scanner;

@Getter
public class ServiceController {


    @Setter
    private ServiceRepository sr;
    public ServiceController(){
        this.sr = new ServiceRepository();
    }

    public void newService(Scanner scanner){
        System.out.println("Alta de servicio/especialidad:");
        System.out.println("-".repeat(35));
        System.out.println("Ingrese sistema operativo");
        var ref = new Object() {
            int i = 0;
        };
        Arrays.stream(OpSys.values()).toList().forEach(sys ->{
            ref.i ++;
            System.out.println(ref.i + " " + sys);
        });
        boolean cont;
        OpSys opSys;
        do{
            try{
                System.out.println("Seleccione una opcion:");
                opSys = Arrays.stream(OpSys.values()).toList().get(Integer.parseInt(scanner.nextLine()) - 1);
                cont = true;
            } catch (Exception e) {
                opSys = null;
                cont = false;
                System.out.println("Indice fuera de rango, intente nuevamente");
            }
        } while(!cont);
        System.out.println("Ingrese software asociado");
        ref.i = 0;
        Arrays.stream(Software.values()).toList().forEach(sys ->{
            ref.i ++;
            System.out.println(ref.i + " " + sys);
        });
        Software software;
        do{
            try{
                System.out.println("Seleccione una opcion:");
                software = Arrays.stream(Software.values()).toList().get(Integer.parseInt(scanner.nextLine()) - 1);
                cont = true;
            } catch (Exception e) {
                software = null;
                cont = false;
                System.out.println("Indice fuera de rango, intente nuevamente");
            }
        } while(!cont);
        Service service = new Service();
        service.setOpSys(opSys);
        service.setSoftware(software);

        sr.getEm().getTransaction().begin();
        sr.addService(service);
        sr.getEm().getTransaction().commit();
    }

    public void showServices() {
        sr.getServices().forEach(System.out::println);
    }

    public Service getServiceById(long id){
        return sr.getServiceById(id);
    }
}
