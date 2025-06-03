Ótima pergunta! Seguir boas práticas no Git ao lidar com a pasta `dist/` depende do fluxo de deploy que você deseja.

### 🔹 **Boas práticas para arquivos gerados (`dist/`)**
1. **💡 Normalmente, NÃO se versiona `dist/` no Git**  
   - A pasta `dist/` é gerada a partir do código-fonte (`src/`).  
   - Ela pode ser reconstruída a qualquer momento com `npm run build`.  
   - **Boas práticas recomendam** adicionar `dist/` ao `.gitignore` para evitar versioná-la.

2. **🚀 Alternativa: Criar um branch ou tag específica para releases**
   - Se quiser manter um histórico da versão **já compilada**, uma opção válida é criar um branch separado:
   ```sh
   git checkout -b release-0.1
   git add dist/
   git commit -m "Versão 0.1 - arquivos compilados"
   git push origin release-0.1
   ```
   - Assim, o código-fonte fica separado da versão pronta para deploy.

2.1 **Criar a tag na branch `main` após o merge**
- Faz o **merge primeiro** (`deploy` → `main`), e depois cria a tag `v0.1` na `main`.
- A tag **marca um commit que está na `main`**, garantindo que ele represente a versão final pós-merge.
- Mais seguro para fluxo de produção, pois reflete **exatamente** o que foi implantado.

📌 **Exemplo de fluxo:**  
```sh
git checkout main
git merge deploy
git tag -a v0.1 -m "Versão 0.1 - Protótipo"
git push origin v0.1
git push origin main
```

3. **📦 Melhor opção: Usar GitHub Actions ou CI/CD**
   - Em vez de enviar `dist/` manualmente, configure um fluxo de **deploy automático**:
   - Quando um commit/tag é feito, o servidor **gera `dist/` e faz o deploy** sem precisar versioná-la.
   - **Plataformas como Vercel ou Netlify** fazem isso nativamente!

### 🔹 **O que fazer agora?**
✅ **Se quiser seguir boas práticas**, adicione `dist/` ao `.gitignore`:  
   ```sh
   echo "dist/" >> .gitignore
   git rm -r --cached dist/
   git commit -m "Removendo dist do versionamento"
   git push origin main
   ```
✅ **Se precisar versionar `dist/`, crie um branch separado para releases**.  
✅ **Se quiser automatizar deploys, posso te ajudar a configurar GitHub Actions ou Netlify**!  

0-0----------------------

Ótimo! Aqui estão duas abordagens para **deploy automático na Hostinger**, dependendo do nível de controle e automação que você deseja. 🚀  

---

## **🔹 Opção A: Branch de release + Deploy Automático da Hostinger**  

### **1️⃣ Criar uma branch de release**  
Se quiser manter um histórico de versões antes de enviar para produção, crie uma branch separada para releases:  
```sh
git checkout -b release-0.1
git push origin release-0.1
```

### **2️⃣ Configurar Hostinger para deploy automático**  
1. **Acesse seu painel da Hostinger** e vá até `Sites → Gerenciar → Git`.  
2. Clique em **Criar Novo Repositório** e conecte ao seu repositório GitHub.  
3. Defina a **branch de release (`release-0.1`)** como fonte do deploy.  
4. **Ative a Implantação Automática**, copiando a **URL do webhook** fornecida pela Hostinger.  

### **3️⃣ Configurar Webhook no GitHub**  
1. No **GitHub**, vá para `Configurações → Webhooks`.  
2. Clique em **"Adicionar Webhook"**.  
3. Cole a **URL do Webhook da Hostinger** e selecione **"Just push event"**.  
4. Agora, **sempre que houver um push na branch `release-0.1`, Hostinger fará o deploy automaticamente!** 🎉  

---

## **🔹 Opção B: Deploy usando GitHub Actions + Hostinger**  

### **1️⃣ Criar a chave SSH para acesso ao servidor Hostinger**  
1. No terminal, gere uma chave SSH:  
   ```sh
   ssh-keygen -t rsa -b 4096 -C "deploy@hostinger"
   ```
2. Copie a chave pública (`.ssh/id_rsa.pub`) e adicione no **Hostinger** (`Configurações → SSH`).  

### **2️⃣ Adicionar a chave privada ao GitHub**  
1. No GitHub, vá para `Configurações → Secrets and variables → Actions`.  
2. Crie um secret chamado **`DEPLOY_SSH_KEY`** e cole a chave privada (`.ssh/id_rsa`).  

### **3️⃣ Criar o Workflow do GitHub Actions**  
Dentro do repositório, crie o arquivo `.github/workflows/deploy.yml` e adicione:  

```yaml
name: Deploy para Hostinger

on:
  push:
    branches:
      - main  # Ou a branch que deseja usar

jobs:
  deploy:
    runs-on: ubuntu-latest

    steps:
    - name: 📥 Checkout do repositório
      uses: actions/checkout@v3

    - name: 🚀 Deploy via SSH
      uses: appleboy/ssh-action@master
      with:
        host: "SEU_HOSTINGER_IP"
        username: "SEU_USUARIO"
        key: "${{ secrets.DEPLOY_SSH_KEY }}"
        script: |
          cd /caminho/para/seu-site
          git pull origin main
          npm install
          npm run build
          cp -r dist/* /public_html/
```

### **4️⃣ Testar o Deploy Automático**  
Agora, **sempre que um push for feito**, o GitHub acionará o workflow e fará o deploy diretamente na Hostinger! 🚀  

---

## **📌 Qual opção escolher?**
✅ **Se quiser simplicidade**, use **deploy automático via Webhook (Opção A)**.  
✅ **Se precisar de mais controle e execução de comandos**, prefira **GitHub Actions (Opção B)**.  

Qual delas faz mais sentido para seu projeto? 😃  
Se precisar de ajustes ou refinamentos, me avise! 🚀  

