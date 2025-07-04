#!/bin/bash

# chmod +x deploy.sh
# ./deploy.sh nome-da-branch nome-da-tag


# Definir parâmetros
BRANCH_NAME=${1:-release}   # Se omitido, padrão é "release"
TAG_VERSION=$2              # Se omitido, não cria tag

echo "🔹 Criando a branch órfã '$BRANCH_NAME'..."
git checkout --orphan $BRANCH_NAME

echo "🗑️  Removendo todos os arquivos do repositório..."
git rm -rf .

# Criando um diretório temporário para armazenar os arquivos da pasta dist
echo "📂 Movendo apenas o conteúdo da pasta 'dist/'..."
mkdir temp_dist
mv dist/* temp_dist/
rm -rf dist

# Restaurando os arquivos para a raiz
mv temp_dist/* .
rmdir temp_dist

echo "✅ Adicionando e commitando os arquivos..."
git add .
git commit -m "Criando branch $BRANCH_NAME com apenas o conteúdo da pasta dist"

echo "🚀 Enviando a branch '$BRANCH_NAME' para o repositório remoto..."
git push origin $BRANCH_NAME || {
  echo "⚠️ Push rejeitado, tentando novamente com '--force'..."
  git push --force origin $BRANCH_NAME
}

# Criar tag se `par2` for fornecido
if [ -n "$TAG_VERSION" ]; then
  echo "🏷️ Criando tag '$TAG_VERSION'..."
  git tag -a $TAG_VERSION -m "Versão $TAG_VERSION"
  git push origin $TAG_VERSION
fi

echo "🎉 Processo concluído! A branch '$BRANCH_NAME' está pronta para deploy."
