package kernel.auth.controller;

import com.pdf.editor.application.DocumentFacade;
import com.pdf.editor.misc.MessageResponse;
import kernel.auth.exception.ResourceNotFoundException;
import kernel.auth.model.User;
import kernel.auth.repository.UserRepository;
import kernel.auth.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;

@CrossOrigin
@RestController
public class UserController {

    @Autowired
    private DocumentFacade documentFacade;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    public User getCurrentUser() {

        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @PostMapping("/deactivateAccount")
    @Transactional
    public ResponseEntity<MessageResponse> deactivateAccount() {

        String email = getCurrentUserEmail();

        try {
            userRepository.deleteByEmail(email);
            documentFacade.deleteAllDocuments(email);
            return ResponseEntity.ok(new MessageResponse("The account of " + email + " deactivated"));
        } catch (Exception exception) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: " + exception.getMessage()));
        }

    }

    private String getCurrentUserEmail() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userPrincipal.getEmail();
    }

}
