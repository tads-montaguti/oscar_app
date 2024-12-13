class ApplicationController < ActionController::Base
    acts_as_token_authentication_handler_for User
    protect_from_forgery with: :null_session

    def configure_permitted_parameters
        devise_parameter_sanitizer.permit(:sign_up, keys: [:name])
    end

    def current_user
        super || User.find_by(authentication_token: request.headers['X-User-Token'], email: request.headers['X-User-Email'])
    end
end
