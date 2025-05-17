package lt.techin.DentistryService.security;

import lt.techin.DentistryService.model.UserProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;

@Service
public class CurrentUserProvider {
  public UserProvider getCurrentProvider() throws AccessDeniedException {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (!(auth.getPrincipal() instanceof UserProvider)) {
      throw new AccessDeniedException("Only providers can perform this action");
    }
    return (UserProvider) auth.getPrincipal();
  }

  public String getCurrentLicenceNumber() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserProvider userProvider = (UserProvider) authentication.getPrincipal();
    return userProvider.getLicenceNumber();
  }

}
