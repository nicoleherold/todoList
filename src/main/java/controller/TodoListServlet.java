package controller;

import model.todo.Todo;
import model.todo.TodoList;
import model.todo.TodoNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Date;
import java.util.Locale;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE_TIME;


@WebServlet(urlPatterns= "/todos")


public class TodoListServlet extends HttpServlet {

    private static TodoList todoList = new TodoList();


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

          //Geht einfacher mit getLocale()
        String headers = request.getHeader("Accept-Language");
        String language = headers.substring(0,3);
        String country = headers.substring(3,5);
        //Locale locale = new Locale(language, country);



  //      Locale locale = request.getLocale();



//        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT ).withLocale( locale );


/*
        LocalDate date = LocalDate.now();
        String text = date.format(ISO_LOCAL_DATE);
        LocalDate parsedDate = LocalDate.parse(text, ISO_LOCAL_DATE);*/


//        DateTimeFormatter d = DateTimeFormatter.ofPattern(String.valueOf(ISO_LOCAL_DATE), locale);



        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html><html><head><link href=\"listStyles.css\" rel=\"stylesheet\"></head><body>");
            out.println(" <h1>Todo List</h1>");

            for (Todo todos : todoList.getTodos()) {
                out.println(" <h2><b> " + todos.getTitle() + " </b></h2>");
                out.println(" <h3> " + todos.getCategory() + " </h3>");
                out.println(" <h3> " + todos.getDueDate() + " </h3>");
//                out.println(" <h3> " + request.getLocale() + " </h3>");

                int listId = todos.getId();
                out.println(" <h3><form action='todos' method='POST'><input name='id' type='hidden' value='" + listId + "'><input type='submit' value='done/delete'></input></form></h3>");
                out.println(" <h3>--------------------------------------</h3>");
            }
            out.println(" <h2><a href='/todo.html'>New Todo</a></h2>");
            out.println("</body></html>");
        }


/*            RequestDispatcher view = request.getRequestDispatcher("todo.html");

            try {
                view.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            }*/
        }



        public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {

            response.setContentType("text/html");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");



            if (request.getParameter("id") != null) {
                int id = Integer.parseInt(request.getParameter("id"));
                try {
                    this.todoList.removeTodo(id);
                } catch (TodoNotFoundException e) {
                    e.printStackTrace();
                }
                doGet(request, response);

            } else {

                // Get value from textField
                String title = request.getParameter("todo");
                String category = request.getParameter("category");
                LocalDate date = LocalDate.parse(request.getParameter("until"));


                Todo todo = new Todo(title, category, date);
                //System.out.println(todo);
                // add record into list
                this.todoList.addTodo(todo);

                doGet(request, response);


            }
        }


}
