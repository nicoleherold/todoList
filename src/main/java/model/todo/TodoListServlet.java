package model.todo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;


@WebServlet("/todos")


public class TodoListServlet extends HttpServlet {

    private static TodoList todoList = new TodoList();


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType("text/html");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");


            RequestDispatcher view = request.getRequestDispatcher("todo.html");

            try {
                view.forward(request, response);
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }



        public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException {

            response.setContentType("text/html");
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");

            // Get value from textField
            String title = request.getParameter("todo");
            String category = request.getParameter("category");
            LocalDate date = LocalDate.parse(request.getParameter("until"));


            // User can't allow to leave TextField empty
//            if (!title.equals("") && !category.equals("")) {
                Todo todo = new Todo(title, category, date);
                System.out.println(todo);
                // add record into list
                this.todoList.addTodo(todo);

//            }


            try (PrintWriter out = response.getWriter()) {
                out.println("<!DOCTYPE html><html><head><link href=\"listStyles.css\" rel=\"stylesheet\"></head><body>");
                out.println(" <h1>Todo List</h1>");

                for (Todo todos : todoList.getTodos()) {
                    out.println(" <h2> " + todos.getTitle() + " </h2>");
                    out.println(" <h3> " + todos.getCategory() + " </h2>");
                    out.println(" <h3> " + todos.getDueDate() + " </h2><br/><br/>");
                }
                out.println(" <a href='/todo.html'>New Todo</a>");
                out.println("</body></html>");
            }
        }
    }
