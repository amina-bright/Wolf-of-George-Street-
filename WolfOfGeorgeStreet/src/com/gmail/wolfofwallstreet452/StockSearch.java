package com.gmail.wolfofwallstreet452;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WolfOfGeorgeStreet
 */
@WebServlet("/stocksearch")
public class StockSearch extends HttpServlet {
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StockSearch() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//String data=StockInfoInteractor.fetchStockDataIntraDay("MSFT");
		//System.out.println(StockInfoInteractor.getVolumeIntraDay(data));
		request.getRequestDispatcher("/jsps/stocksearch.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}