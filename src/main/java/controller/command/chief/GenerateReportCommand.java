package controller.command.chief;

import javax.servlet.ServletException;
import controller.command.FrontCommand;
import entity.Product;
import entity.User;
import service.CashRegisterService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GenerateReportCommand extends FrontCommand {

    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
        String typeOfReport ="";
        if (request.getParameter("report") != null) {
            typeOfReport = request.getParameter("report");
        }
        if (typeOfReport.equals("top10")) {
            List<Product> top10Products = cashRegisterService.generateTop10Report();
            if (top10Products != null) {
                request.setAttribute("message", "TOP 10 PRODUCTS");
                request.setAttribute("report", "report");
                request.setAttribute("top10", top10Products);
                forward("chief/reports/top-10-products");
                return;
            }
         }
        if (typeOfReport.equals("best-operators")) {
            Map<User, Integer> bestOperators = cashRegisterService.generateBestOperatorsReport();
            if (bestOperators != null) {
                request.setAttribute("message", "Best operators");
                request.setAttribute("report", "report");
                request.setAttribute("bestoperators", bestOperators);
                forward("chief/reports/best-operators");
                return;
            }
          }

       forward("chief/generate-report");

    }

}
