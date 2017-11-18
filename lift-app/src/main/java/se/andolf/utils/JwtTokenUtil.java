package se.andolf.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.xml.bind.DatatypeConverter;
import java.util.function.Function;

/**
 * @author Thomas on 2017-11-05.
 */
public final class JwtTokenUtil {

    private JwtTokenUtil() {
    }

    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private static Claims getAllClaimsFromToken(String token) {

        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary("c2VjcmV0"))
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
}
