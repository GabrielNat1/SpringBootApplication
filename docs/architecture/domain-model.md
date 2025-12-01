# Domain Model

## Entities

- **User**: Represents a system user with username, password, and role.
- **Product**: Represents a product with attributes like id, name, description, price.
- **Cart**: Represents a user's shopping cart, containing products and quantities.

## Relationships

- A `User` can have one `Cart`.
- A `Cart` contains multiple `Product` items.

*Refer to your model classes for specific fields and relationships.*
