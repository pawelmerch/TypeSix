package org.shlimtech.typesix.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.shlimtech.typesix.dto.UserDTO;
import org.shlimtech.typesix.model.User;
import org.shlimtech.typesix.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    private void complement(User user, UserDTO userDTO) {
        if (userDTO.getGithubLink() != null) {
            user.setGithubLink(userDTO.getGithubLink());
        }
        if (userDTO.getVkLink() != null) {
            user.setVkLink(userDTO.getVkLink());
        }

        // TODO more fields to complement
    }

    public void createOrComplementUser(UserDTO userDTO) {
        User user = userRepository.findByEmail(userDTO.getEmail());

        if (user == null) {
            user = modelMapper.map(userDTO, User.class);
        }

        complement(user, userDTO);
        userRepository.save(user);
    }

    @Transactional
    public UserDTO loadUser(String email) {
        User user = userRepository.findByEmail(email);
        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public UserDTO loadUser(int id) {
        User user = userRepository.getReferenceById(id);
        return modelMapper.map(user, UserDTO.class);
    }

}
