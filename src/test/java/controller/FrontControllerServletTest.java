package controller;

import controller.command.FrontCommand;
import controller.command.LoginCommand;
import junit.framework.TestCase;
import service.CashRegisterService;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class FrontControllerServletTest extends TestCase {

    private final static String PAGE = "/WEB-INF/index.jsp";

    public void testDoGet() {
    }

    public void testDoPost() throws ServletException, IOException {




        final ServletContext servletContextMock = mock(ServletContext.class);
        final HttpServletRequest requestMock = mock(HttpServletRequest.class);
        final HttpServletResponse responseMock = mock(HttpServletResponse.class);
        final HttpSession sessionMock = mock(HttpSession.class);
        when(requestMock.getSession()).thenReturn(sessionMock);


        final FrontCommand command = new FrontCommand() {
            @Override
            public void process(CashRegisterService cashRegisterService) throws ServletException, IOException {
            }
            @Override
            public void init(ServletContext servletContext, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
                this.context = servletContextMock;
                this.request = requestMock;
                this.response = responseMock;
            }
        };
        command.init(servletContextMock,requestMock, responseMock );


        final FrontControllerServlet servlet = new FrontControllerServlet(){
            public ServletContext getServletContext() {
                return servletContextMock; // return the mock
            }
            @Override
            protected FrontCommand getCommand(HttpServletRequest request) {
                return command;
            }
        };


        servlet.doPost(requestMock, responseMock);

        verify(requestMock, never()).getSession();
        verify(requestMock, times(1)).setCharacterEncoding("utf8");
        verify(responseMock, never()).sendRedirect("index.jsp");





    }
}