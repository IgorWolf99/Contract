package services;

import java.time.LocalDate;

import entities.Contract;
import entities.Installment;

public class ContractService {
    
    private OnlinePaymentService onlinePaymentService;

    public ContractService(OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }

    public void processContract(Contract contract, int months){
        // Valor por parcela
        double basicQuota = contract.getTotalValue() / months;

        for (int month=1; month <= months; month++){
            LocalDate dueDate = contract.getDate().plusMonths(month);

            // Juros por mês
            double interest = onlinePaymentService.interest(basicQuota, month);
            // Taxa do pagamento encima do juros mensal
            double fee = onlinePaymentService.paymentFee(basicQuota + interest);
            // Resultado final da parcela
            double quota = basicQuota + interest + fee;

            // A cada loop, cria uma nova prestação no contrato com as regras já inclusas. 
            contract.getInstallment().add(new Installment(dueDate, quota));
        }


    }
}
