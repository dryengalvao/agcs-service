#!/bin/bash

# Define cores para saída no terminal
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m' # No Color

# Função para exibir mensagens de erro e sair
error_exit() {
    echo -e "${RED}ERRO: $1${NC}" >&2
    exit 1
}

# Verifica se o Docker está instalado
if ! command -v docker &> /dev/null; then
    error_exit "Docker não está instalado. Por favor, instale-o e tente novamente."
fi

# Verifica se o Maven está instalado
if ! command -v mvn &> /dev/null; then
    error_exit "Maven não está instalado. Por favor, instale-o e tente novamente."
fi

# Executa os testes unitários disponiveis
echo -e "${GREEN}Executando testes unitários e de integração com Maven...${NC}"
mvn clean test || error_exit "Falha nos testes. Corrija os testes antes de prosseguir."

# Compila a aplicação após a execução dos testes
echo -e "${GREEN}Testes passaram com sucesso! Compilando a aplicação com Maven...${NC}"
mvn package || error_exit "Falha na compilação com Maven."

# Constroi a imagem de acordo com a definição do Dockerfile do projeto
echo -e "${GREEN}Construindo a imagem Docker 'agcs-service'...${NC}"
docker build -f Dockerfile.agcs -t agcs-service:latest . || error_exit "Falha ao construir a imagem Docker."

# Faz o deploy da imagem gerada disponibilizando para uso
echo -e "${GREEN}Executando o container 'agcs-service-container'...${NC}"
docker run -d --name agcs-service-container -p 8086:8086 agcs-service:latest || error_exit "Falha ao executar o container."


echo -e "${GREEN}Aplicação AGCS iniciada com sucesso! Acesse em http://localhost:8086${NC}"
echo "Para visualizar os logs do Container, use: docker logs agcs-service-container"
echo "Para parar o container, use: docker stop agcs-service-container"
echo "Para remover o container, use: docker rm agcs-service-container"
echo "Para remover a imagem, use: docker rmi agcs-service:latest"


