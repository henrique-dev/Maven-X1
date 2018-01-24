# Maven-X1
## Robô quadrupede

Este projeto se econtra em atualização.

Passos a serem concluidos:
- [ ] Implementação do MPU9250
- [ ] Movimentação autônoma

Passos concluídos:
- [x] Robô se movimentando

### Descrição

Construido a partir de um modelo 3D, seu controle é realizado através da comunicação
entre as aplicações deste com a de um smartphone. Ambas as aplicações foram feitas em Java.

Sua movimentação se dá por movimentos já pré-determinados, sendo nessario um esquema algoritmico. 

Seu hardware é composto por:
- 1 Raspberry pi zero
- 1 Módulo PWM PCA9685
- 1 MPU 9250
- 12 Servo Motor MG995

Na raiz do projeto existem três diretórios: 

**Aplicação Controle** se encontra o código relativo à aplicação do smartphone
para Android (foi utilizado o ambiente AndroidStudio para criação deste, portanto é possivel importar o projeto a partir
deste diretorio mesmo).

**Modelos 3D** se encontra as peças modeladas em 3D do robô. Obs.: Por falha minha, algumas peças fiacaram ruins de encaixar,
portanto foi necessário um mínimo ajuste manual em alguma delas, mas nada comprometeu a estrutura final. Os modelos ainda serão
atualizados para se adequarem perfeitamente.

**Raspberry** se encontra o código relativo à aplicação do robô.

