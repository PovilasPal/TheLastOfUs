package lt.techin.DentistryService.security;

import lt.techin.DentistryService.model.UserClient;
import lt.techin.DentistryService.model.UserProvider;
import lt.techin.DentistryService.service.UserClientService;
import lt.techin.DentistryService.service.UserProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  private final UserClientService userClientService;
  private final UserProviderService userProviderService;

  @Autowired
  public UserDetailsServiceImpl(UserClientService userClientService, UserProviderService userProviderService) {
    this.userClientService = userClientService;
    this.userProviderService = userProviderService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    Optional<UserClient> foundUserClient = this.userClientService.findByUsername(username);

    if (foundUserClient.isPresent()) {
      return foundUserClient.get();
    }

    Optional<UserProvider> foundUserProvider = this.userProviderService.findByUsername(username);

    if (foundUserProvider.isPresent()) {
      return foundUserProvider.get();
    }

    throw new UsernameNotFoundException(username);

  }
}

