Rails.application.routes.draw do
  devise_for :users, path: 'auth', controllers: {
    sessions: 'user/sessions'
  }

  resources :votes
  resources :users
    
  root "users#index"
end