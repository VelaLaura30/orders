Feature: Pedido de bebida desde el frontend

  Como cliente
  Quiero pedir una bebida específica desde el frontend de React
  Para verificar si fue aceptada correctamente por el sistema
  Considerando que la bebida debe existir en el menú de Angular

  Scenario: Pedido exitoso de una bebida disponible
    Given que la bebida "Test Espresso" está registrada en el menú
    When el cliente pide la bebida "Test Espresso" desde el frontend de React
    Then el sistema debe registrar el pedido con la bebida "Test Espresso"

  Scenario: Pedido de bebida no disponible
    Given que la bebida "Fantasía de Unicornio" no está en el menú
    When el cliente intenta pedir la bebida "Fantasía de Unicornio" desde el frontend de React
    Then el sistema debe mostrar un mensaje indicando que la bebida no está disponible
