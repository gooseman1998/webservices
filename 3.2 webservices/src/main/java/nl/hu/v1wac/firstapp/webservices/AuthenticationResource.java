package nl.hu.v1wac.firstapp.webservices;



import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import nl.hu.v1wac.firstapp.persistence.UserDao;
import nl.hu.v1wac.firstapp.persistence.UserDaoImpl;
import nl.hu.v1wac.firstapp.persistence.UserDao;
import nl.hu.v1wac.firstapp.persistence.UserDaoImpl;

import javax.print.attribute.standard.Media;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Key;
import java.util.AbstractMap.SimpleEntry;
import java.util.Calendar;

@Path("/authentication")
public class AuthenticationResource {
    final static public Key key = MacProvider.generateKey();

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response authenticateUser(@FormParam("username") String username, @FormParam("password") String password){
        try {
            // Authenticate the user against the database
            System.out.println(username+": "+password);
            UserDao dao = new UserDaoImpl();
            String role = dao.findRoleForUser(username, password);

            if(role == null) {throw new IllegalArgumentException("No user found!");}

            String token = createToken(username, role);
            SimpleEntry<String, String> JWT = new SimpleEntry<String, String>("JWT", token);

            System.out.println(Response.ok(JWT));
            System.out.println(Response.ok(JWT).build());
            return Response.ok(JWT).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

    private String createToken(String username, String role) throws JwtException {
        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.MINUTE, 30);

        return Jwts.builder()
                .setSubject(username)
                .setExpiration(expiration.getTime())
                .claim("role", role)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }
}