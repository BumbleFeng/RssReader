package servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import pojo.Feed;

/**
 * Servlet implementation class subscribe
 */
@WebServlet("/subscribe")
public class Subscribe extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ArrayList<Feed> feeds = new ArrayList<Feed>();
    private HttpSession session;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Subscribe() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
	    rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("url");
		session = request.getSession();
        feeds = (ArrayList<Feed>) session.getAttribute("feeds");
        if(feeds==null) {
        	 	feeds = new ArrayList<Feed>();
        } else {
        		for (Feed feed:feeds) {
				if(feed.getUrl().equals(url)) {
					refresh();
				    RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
				    rd.forward(request, response);
					return;
				}
        		}
        }
        for (int i = 0; i< feeds.size();i++) {
        		if(feeds.get(i).getChannel().getTitle().equals("Invalid URL"))
        			feeds.remove(i);
        }
        if(url!="") {
        		Feed feed = new Feed(url);
        		feeds.add(feed);
        }
        	session.setAttribute("feeds",feeds);
        refresh();
        RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
	}
	
	private void refresh() {
		if(!feeds.isEmpty()) {
    			for (Feed feed : feeds) {
    				feed.getFeed();
    				if(feed.getItems()==null) {
    					feed.getChannel().setTitle("Invalid URL");
    				} else {
    					feed.setChannel(feed.getItems().remove(0));
    				}
    			}
    			session.setAttribute("feeds",feeds);
		}
	}

}
