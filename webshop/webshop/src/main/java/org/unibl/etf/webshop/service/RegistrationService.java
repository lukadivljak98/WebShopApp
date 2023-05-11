package org.unibl.etf.webshop.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.unibl.etf.webshop.exception.ConfirmationTokenNotFoundException;
import org.unibl.etf.webshop.model.AppUser;
import org.unibl.etf.webshop.model.AppUserRole;
import org.unibl.etf.webshop.request.RegistrationRequest;
import org.unibl.etf.webshop.security.ConfirmationToken;
import org.unibl.etf.webshop.security.EmailSender;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationService {

    private final AppUserService appUserService;
    private final EmailValidator emailValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getMail());
        if(!isValidEmail){
            log.warn("email not valid");
            //throw new IllegalStateException("email not valid");
            return "Email not valid";
        }
        String username = request.getUsername();
        String email = request.getMail();
        if(!appUserService.checkUsername(username)){
            log.warn("Username: " + username + " is already taken");
            return "Username is already taken";
        }
        if(!appUserService.checkEmail(email)){
            log.warn("Email:" + email + " is already taken");
            return "Email is already taken";
        }
        String token =  appUserService.signUpUser(new AppUser(
                request.getName(), request.getLastName(), request.getCity(), request.getUsername()
                ,request.getPassword(),request.getMail(), AppUserRole.USER
        ));
        log.info("User successfully registered: " + request);
        emailSender.send(request.getMail(), buildEmail(request.getName(), token));
        return token;
    }

    public String confirmToken(String token) {
        Optional<ConfirmationToken> confirmationToken = confirmationTokenService.getToken(token);
        if(confirmationToken.isEmpty()){
            log.error("Token: " + token + " not found");
            return "token not found";
        }
        ConfirmationToken conToken = confirmationTokenService.getToken(token).orElseThrow(()->
            new ConfirmationTokenNotFoundException("Token not found: " + token)
        );
        if(conToken.getConfirmedAt() != null){
            log.error("email already confirmed");
            //throw new IllegalStateException("email already confirmed");
            return "Email is already confirmed";
        }
        LocalDateTime expiredAt = conToken.getExpiresAt();
        //if(expiredAt.isBefore(LocalDateTime.now())){
            //throw new IllegalStateException("token expired");
        //}
        //String realToken  = confirmationTokenService.getRealToken(confirmationToken.getAppUser().getId());
        //if(realToken.equals(confirmationToken.getToken())) {
            confirmationTokenService.setConfirmedAt(token);
            appUserService.enableAppUser(conToken.getAppUser().getMail());
            return "confirmed";
       // }
        //else return "invalid token";
    }

    private String buildEmail(String name, String token){
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Your pin is: " + token + " </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">  </p></blockquote>\n. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
