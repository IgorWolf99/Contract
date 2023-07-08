import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import entities.Contract;
import entities.Installment;
import services.ContractService;
import services.PaypalPayment;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        System.out.println("Entre com os dados do contrato:");
        System.out.print("Numero: ");
        int number = sc.nextInt();
        System.out.print("Data (dd/MM/yyyy): ");
        LocalDate date = LocalDate.parse(sc.next(), dtf);
        System.out.print("Valor do contrato: R$ ");
        double totalValue = sc.nextDouble();

        // Instancia 1 contrato 
        Contract contract = new Contract(number, date, totalValue);

        System.out.print("Numero de parcelas: ");
        int installments = sc.nextInt();

        // Instancia o servico de contrato e qual serviço sera utilizado 
        ContractService contractService = new ContractService(new PaypalPayment());

        // Cria o contrato e as parcelas
        contractService.processContract(contract, installments);

        System.out.println("\nPARCELAS:");
        
        for (Installment installmentsInContract : contract.getInstallment()){
            System.out.println(installmentsInContract);
        } 

        sc.close();
    }
}
