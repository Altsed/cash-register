package controller.command.stockman;

import controller.command.FrontCommand;
import entity.Product;
import service.CashRegisterService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class WelcomeCommand extends FrontCommand {
    private int getCurrentPage() {
        if (request.getParameter("currentPage") != null){
            return Integer.parseInt(request.getParameter("currentPage"));
        }
        return 1;
    }
    private int getRecordsPerPage(){
        if (request.getParameter("recordsPerPage") != null){
            return Integer.parseInt(request.getParameter("recordsPerPage"));
        }
        return 10;
     }

    @Override
    public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {


        int currentPage = getCurrentPage();
        int recordsPerPage = getRecordsPerPage();

        List<Product> products = cashRegisterService.getProducts(currentPage, recordsPerPage);


        int rows = cashRegisterService.getNumberOfRows();

        int nOfPages = rows / recordsPerPage;

        if (nOfPages % recordsPerPage > 0) {
            nOfPages++;
        }

        request.setAttribute("products", products);
        request.setAttribute("noOfPages", nOfPages);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("recordsPerPage", recordsPerPage);

        forward( "stockman/welcome-page");

    }
}
