
**Em vez de manter dist/ na raiz da branch release, mova os arquivos para a raiz e remova a pasta:**

```bash
cd dist
mv * ..
cd ..
rm -rf dist
git add .
git commit -m "Movendo arquivos de dist para raiz"
git push origin release
```
Isso garante que apenas os arquivos serão enviados, sem a pasta dist/.

---------------------------------------

Se a branch release já existe, remova todo o conteúdo antigo:

git checkout release
git rm -r --cached .
git commit -m "Limpando a branch release"
git push origin release

Agora, adicione apenas a pasta dist/:

git rm -r --cached dist/
echo "!" >> .gitignore
echo "!dist/" >> .gitignore
git add dist/
git commit -m "Adicionando apenas dist à branch release"
git push origin release
