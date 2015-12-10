package security;

import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;
import java.text.ParseException;

public class UserSecurityHelper {

    public static Long GetUserIdFromToken(String token) throws ParseException{
        token = token.substring("Bearer ".length());
        
        SignedJWT signedJWT = SignedJWT.parse(token);
        JWSVerifier verifier = new MACVerifier(Secrets.ADMIN);

        return Long.parseLong(signedJWT.getJWTClaimsSet().getSubject());
    }
}
