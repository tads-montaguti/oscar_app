class User::SessionsController < Devise::SessionsController
  respond_to :json

  # POST /users/sign_in
  def create
    if request.format.html?
      super
    else
      user = User.find_by(email: params[:email])

      if user&.valid_password?(params[:password])
        sign_in(user)
        render json: user, status: :ok
      else
        render json: { error: "Invalid email or password" }, status: :unauthorized
      end
    end
  end

  # DELETE /users/sign_out
  def destroy
    if request.format.html?
      super
    else
      sign_out(current_user)
      render json: { message: "Logged out successfully" }, status: :ok
    end
  end
end