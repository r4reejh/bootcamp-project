package com.shell;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class MainController {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public StandardRes hello(){
        return new StandardRes(
                true,
                "SalesOp API for consumer USE"
        );
    }

    @GET
    @Path("/find/accountId/{accountId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String findAccountByID(@PathParam("accountId") String AccountId){
        if(AccountId == null)
            return "invalid";
        return SalesforceUtil.findOpportunityByAccountId(AccountId);
    }

    @GET
    @Path("/find/accounts/probability-gt/{probability}")
    @Produces(MediaType.APPLICATION_JSON)
    public String findProbabilityGreaterThan(@PathParam("probability") String probability){
        if(probability == null)
            return "invalid";
        return SalesforceUtil.getUsersGreaterThanXProbability(probability);
    }
}

class StandardRes {
    public boolean success;
    public String message;

    public StandardRes(boolean status, String message) {
        this.success = status;
        this.message = message;
    }
}