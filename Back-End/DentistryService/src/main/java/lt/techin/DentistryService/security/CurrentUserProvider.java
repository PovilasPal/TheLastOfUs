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

  public String getCurrentLicenseNumber() throws AccessDeniedException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null || !(authentication.getPrincipal() instanceof UserProvider)) {
      throw new AccessDeniedException("No authenticated provider");
    }
    return ((UserProvider) authentication.getPrincipal()).getLicenseNumber();
  }

}
