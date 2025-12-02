const LOCAL_STORAGE_KEY = 'aeds_tp4_presentes';
let products = [];
let nextId = 1;

function showMessage(message, type = 'info') {
    const box = document.getElementById('messageBox');
    box.textContent = message;
    box.className = '';
    box.classList.add('p-3','rounded','mb-3');

    if (type === 'success') box.classList.add('bg-green-200','text-green-900');
    else if (type === 'error') box.classList.add('bg-red-200','text-red-900');
    else box.classList.add('bg-blue-200','text-blue-900');

    setTimeout(() => box.classList.add('hidden'), 3500);
}

function getLocal() {
    try {
        return JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY)) || [];
    } catch (e) {
        return [];
    }
}

function saveLocal(data) {
    localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(data));
}

function generateBinaryStructure() {
    const view = document.getElementById('binaryStructureView');
    
    if (products.length === 0) {
        view.textContent = "Arquivo vazio (apenas cabeçalho de 4 bytes)\n\n" +
                          "Cabeçalho: [00000000] [00000000] [00000000] [00000000] (último ID = 0)";
        return;
    }
    
    let output = "Cabeçalho (4 bytes):\n";
    output += `[${(nextId-1).toString(2).padStart(32, '0').match(/.{8}/g).join('] [')}] (último ID = ${nextId-1})\n\n`;

    
    output += "--------------------------------------------------\n\n";
    
    // Simula como ficaria no arquivo binário
    let currentPosition = 4; // Começa após o cabeçalho
    
    products.forEach((prod, index) => {
        output += `Registro ${index+1} (ID: ${prod.id}) - Posição: 0x${currentPosition.toString(16).toUpperCase()}\n`;
        
        // Lápide 
        output += "  Lápide:     [00100000] (0x20 = ' ' = ativo)\n";
        
        // Calcula tamanho aproximado do registro em bytes
        // Simula: id(4) + campos de string (nome + descricao + categoria + prioridade + observacoes)
        const nomeBytes = new TextEncoder().encode(prod.nome || '').length;
        const descricaoBytes = new TextEncoder().encode(prod.descricao || '').length;
        const categoriaBytes = new TextEncoder().encode(prod.categoria || '').length;
        const prioridadeBytes = new TextEncoder().encode(prod.prioridade || '').length;
        const observacoesBytes = new TextEncoder().encode(prod.observacoes || '').length;
        
        const totalSize = 4 + // id (int)
                         nomeBytes + 1 + // nome + terminador
                         descricaoBytes + 1 + // descricao + terminador
                         categoriaBytes + 1 + // categoria + terminador
                         prioridadeBytes + 1 + // prioridade + terminador
                         observacoesBytes + 1; // observacoes + terminador
        
        const sizeBytes = totalSize.toString(2).padStart(16, '0');
        output += `  Tamanho:    [${sizeBytes.substring(0,8)}] [${sizeBytes.substring(8)}] (${totalSize} bytes)\n`;
        
        output += "  Dados:\n";
        output += `    ID:          ${prod.id}\n`;
        output += `    Nome:        "${prod.nome || ''}" (${nomeBytes} bytes)\n`;
        output += `    Descrição:   "${prod.descricao || ''}" (${descricaoBytes} bytes)\n`;
        output += `    Categoria:   "${prod.categoria || ''}" (${categoriaBytes} bytes)\n`;
        output += `    Prioridade:  "${prod.prioridade || ''}" (${prioridadeBytes} bytes)\n`;
        output += `    Observações: "${prod.observacoes || ''}" (${observacoesBytes} bytes)\n`;
        
        // Representação hexadecimal aproximada
        output += "\n  Representação hexadecimal (simplificada):\n";
        output += "    20 "; // Lápide ' '
        
   
        const sizeLE1 = totalSize & 0xFF;
        const sizeLE2 = (totalSize >> 8) & 0xFF;
        output += `${sizeLE1.toString(16).padStart(2, '0')} ${sizeLE2.toString(16).padStart(2, '0')} `;
        

        for (let i = 0; i < 4; i++) {
            const byte = (prod.id >> (i * 8)) & 0xFF;
            output += `${byte.toString(16).padStart(2, '0')} `;
        }
        
        output += "... [dados dos campos]\n";
        
        currentPosition += 1 + 2 + totalSize; // Lápide + Tamanho + Dados
        
        if (index < products.length - 1) {
            output += "\n  ---\n\n";
        }
    });
    
    output += "\n--------------------------------------------------\n";
    output += `Tamanho total do arquivo: ${currentPosition} bytes\n`;
    output += `Número de registros: ${products.length}`;
    
    view.textContent = output;
}

async function loadAndRenderProducts() {
    products = getLocal();
    nextId = products.length > 0 ? Math.max(...products.map(p => p.id)) + 1 : 1;
    renderProducts();
}

async function handleCreateUpdate(event) {
    event.preventDefault();

    const id = document.getElementById('presentId').value;

    const nome = document.getElementById('pessoa').value.trim();
    const descricao = document.getElementById('nomePresente').value.trim();
    const categoria = document.getElementById('categoria').value.trim();
    const prioridade = document.getElementById('prioridade').value.trim();
    const observacoes = document.getElementById('observacoes').value.trim();

    if (!nome || !descricao || !categoria || !prioridade) {
        showMessage('Preencha todos os campos obrigatórios.', 'error');
        return;
    }

    const newData = {
        nome,
        descricao,
        categoria,
        prioridade,
        observacoes
    };

    if (id) {
        const idx = products.findIndex(p => p.id == id);

        if (idx !== -1) {
            products[idx] = { ...products[idx], ...newData };
            saveLocal(products);
            showMessage(`Sugestão ID ${id} atualizada.`, 'success');
        }
    } else {
        newData.id = nextId++;
        products.push(newData);
        saveLocal(products);
        showMessage(`Sugestão registrada com sucesso!`, 'success');
    }

    loadAndRenderProducts();
    resetForm();
}

window.handleEdit = function(id) {
    const p = products.find(x => x.id === id);
    if (!p) return;

    document.getElementById('presentId').value = p.id;
    document.getElementById('pessoa').value = p.nome;
    document.getElementById('nomePresente').value = p.descricao;
    document.getElementById('categoria').value = p.categoria;
    document.getElementById('prioridade').value = p.prioridade;
    document.getElementById('observacoes').value = p.observacoes;

    document.getElementById('formTitle').textContent = `Alterar Sugestão ID ${id}`;
    document.getElementById('submitButton').textContent = 'Salvar Alterações';
    document.getElementById('cancelEditButton').classList.remove('hidden');
}

window.handleDelete = function(id) {
    products = products.filter(p => p.id !== id);
    saveLocal(products);
    loadAndRenderProducts();
    showMessage(`Sugestão ID ${id} excluída.`, 'success');
}

window.resetForm = function() {
    document.getElementById('presentForm').reset();
    document.getElementById('presentId').value = '';
    document.getElementById('formTitle').textContent = 'Incluir Nova Sugestão de Presente';
    document.getElementById('submitButton').textContent = 'Salvar Sugestão (CREATE/UPDATE)';
    document.getElementById('cancelEditButton').classList.add('hidden');
}

window.handleSearch = function() {
    const q = document.getElementById('searchQuery').value.toLowerCase();
    renderProducts(q);
}

function renderProducts(search = '') {
    const tbody = document.getElementById('presentTableBody');
    const jsonView = document.getElementById('localStorageView');
    const empty = document.getElementById('emptyMessage');

    tbody.innerHTML = '';

    const filtered = products.filter(p =>
        p.nome.toLowerCase().includes(search) ||
        p.descricao.toLowerCase().includes(search) ||
        p.categoria.toLowerCase().includes(search) ||
        p.prioridade.toLowerCase().includes(search) ||
        p.id.toString().includes(search)
    );

    if (filtered.length === 0) empty.classList.remove('hidden');
    else empty.classList.add('hidden');

    filtered.forEach(p => {
        const row = tbody.insertRow();

        row.insertCell().textContent = p.id;
        row.insertCell().textContent = p.nome;
        row.insertCell().textContent = p.descricao;
        row.insertCell().textContent = p.prioridade;

        const acoes = row.insertCell();
        const b1 = document.createElement('button');
        b1.textContent = 'Alterar';
        b1.onclick = () => handleEdit(p.id);
        b1.className = 'px-2 py-1 bg-blue-200 mr-2 rounded';

        const b2 = document.createElement('button');
        b2.textContent = 'Excluir';
        b2.onclick = () => handleDelete(p.id);
        b2.className = 'px-2 py-1 bg-red-200 rounded';

        acoes.appendChild(b1);
        acoes.appendChild(b2);
    });

    // Atualiza ambas as visualizações
    jsonView.textContent = JSON.stringify(products, null, 2);
    generateBinaryStructure();
}

document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('presentForm').addEventListener('submit', handleCreateUpdate);
    loadAndRenderProducts();
});
