# This file should ensure the existence of records required to run the application in every environment (production,
# development, test). The code here should be idempotent so that it can be executed at any point in every environment.
# The data can then be loaded with the bin/rails db:seed command (or created alongside the database with db:setup).
#
# Example:
#
#   ["Action", "Comedy", "Drama", "Horror"].each do |genre_name|
#     MovieGenre.find_or_create_by!(name: genre_name)
#   end


User.create(email: "a@a.com", password: "pokpok", name: "Usuário A")
User.create(email: "b@b.com", password: "pokpok", name: "Usuário B")
User.create(email: "c@c.com", password: "pokpok", name: "Usuário C")
User.create(email: "d@d.com", password: "pokpok", name: "Usuário D")
User.create(email: "e@e.com", password: "pokpok", name: "Usuário E")
