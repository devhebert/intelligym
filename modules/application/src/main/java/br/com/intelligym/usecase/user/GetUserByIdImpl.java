package br.com.intelligym.usecase.user;

import br.com.intelligym.exception.user.ErrorMessages;
import br.com.intelligym.mapper.user.UserMapper;
import br.com.intelligym.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public class GetUserByIdImpl implements GetUserById {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(GetUserByIdImpl.class);

    public GetUserByIdImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public OutputPort execute(InputPort input) {
        try {
            if (input == null) return new OutputPort.Error(ErrorMessages.INVALID_INPUT);

            Optional<OutputPort> result = this.userRepository.findById(input.id())
                    .map(user -> new OutputPort.Ok(UserMapper.INSTANCE.userToUserDTO(user)));

            return result.orElse(new OutputPort.Error(ErrorMessages.USER_NOT_FOUND));
        } catch (Exception e) {
            logger.error("Error get user by id", e);
            return new OutputPort.Error(ErrorMessages.INTERNAL_ERROR);
        }
    }
}
