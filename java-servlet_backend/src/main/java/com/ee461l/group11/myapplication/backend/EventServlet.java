package com.ee461l.group11.myapplication.backend;

import com.googlecode.objectify.ObjectifyService;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by iamda on 11/27/2016.
 */

public class EventServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException
    {
        String eventName = req.getParameter("eventName");
        String eventDesc = req.getParameter("eventDesc");
        String month = req.getParameter("month");
        String date = req.getParameter("date");
        String year = req.getParameter("year");
        Date eventDate = new Date(Integer.parseInt(month), Integer.parseInt(date), Integer.parseInt(year));
        SilvousEvent event = new SilvousEvent(eventName, eventDesc, eventDate, 6, 9);
        ObjectifyService.ofy().save().entities(event).now();

    }
}
