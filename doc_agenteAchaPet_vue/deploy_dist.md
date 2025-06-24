Aqui está o fluxo correto para **criar uma nova branch a partir da `main`, apagar todos os arquivos, exceto uma pasta, sem afetar a `main`**:

```sh
npm run build 
```
### **1️⃣ Criar a nova branch a partir da `main`** (ou não...)
```sh
git checkout -b nova-branch main
```
✅ Isso cria a branch `nova-branch` baseada na `main`.

---

### **2️⃣ Apagar todos os arquivos, exceto uma pasta**
Se quiser **manter apenas a pasta `meu-projeto`**, use:
```sh
git rm -rf . 
git restore --source=main --staged --worktree dist/
```
✅ Isso remove tudo, **exceto** a pasta `meu-projeto`.

---

### **3️⃣ Confirmar as alterações e fazer o commit**
Agora, registre as mudanças:
```sh
git add .
git commit -m "Criando nova branch com apenas a pasta meu-projeto"
```

---

### **4️⃣ Enviar a nova branch para o repositório remoto**
```sh
git push origin nova-branch
```

Agora sua nova branch contém **apenas a pasta desejada**, sem afetar a `main`! 🚀  
Se precisar de ajustes ou quiser excluir outras pastas específicas, me avise! 😃  
Quer que eu te ajude a criar um script para automatizar esse processo?