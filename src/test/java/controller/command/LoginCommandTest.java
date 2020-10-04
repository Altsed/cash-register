package controller.command;

import controller.FrontControllerServlet;
import junit.framework.TestCase;
import service.CashRegisterService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class LoginCommandTest extends TestCase {

    public void testProcess() throws ServletException, IOException {

        final FrontCommand command = mock(FrontCommand.class);
        final HttpServletRequest requestMock = mock(HttpServletRequest.class);
        final HttpServletResponse responseMock = mock(HttpServletResponse.class);
        final ServletContext servletContextMock = mock(ServletContext.class);
        final HttpSession sessionMock = mock(HttpSession.class);

        final LoginCommand loginCommand = new LoginCommand(){
            @Override
            public void init(ServletContext servletContext, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
                this.context = servletContextMock;
                this.request = requestMock;
                this.response = responseMock;

            }
        };
        loginCommand.init(servletContextMock, requestMock, responseMock);


        CashRegisterService cashRegisterService = mock(CashRegisterService.class);

        when(loginCommand.request.getParameter("login")).thenReturn("login");
        when(loginCommand.request.getParameter("password")).thenReturn("password");
        when(loginCommand.request.getSession()).thenReturn(sessionMock);


        loginCommand.process(cashRegisterService);
        String roleName = "stockman";

        verify(requestMock, times(3)).getParameter("login");
        verify(requestMock, times(1)).getSession();
        verify(responseMock, never()).sendRedirect(roleName + "/welcome-page");

    }

}