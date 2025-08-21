(ns bank.core
  (:gen-class))

;; Criar uma transação
(defn criar-transacao [nome valor]
  {:nome nome :valor valor})

;; Verifica se é uma Compra 
(defn e-uma-compra? [transacao]
  (= "compra" (:nome transacao)))

;; Verifica se é uma Venda 
(defn e-uma-venda? [transacao]
  (= "venda" (:nome transacao)))

;; Lista de transações
(def transacoes (atom []))

(defn get-valor [transacoes]
  (map :valor transacoes))

(defn get-compras [transacoes]
  (filter e-uma-compra? transacoes))

(defn get-vendas [transacoes]
  (filter e-uma-venda? transacoes))

(defn calcula-valor [transacoes]
  (reduce + 0 (get-valor transacoes)))

;; -------- MENU INTERATIVO ----------
(defn menu []
  (loop []
    (println "\n===== MENU BANCO =====")
    (println "1 - Adicionar Compra")
    (println "2 - Adicionar Venda")
    (println "3 - Listar Transações")
    (println "4 - Total de Compras")
    (println "5 - Total de Vendas")
    (println "0 - Sair")
    (print "Escolha uma opção: ") (flush)
    (let [opcao (read-line)]
      (cond
        (= opcao "1") (do
                        (print "Digite o valor da compra: ") (flush)
                        (let [valor (Double/parseDouble (read-line))]
                          (swap! transacoes conj (criar-transacao "compra" valor)))
                        (println "Compra adicionada!")
                        (recur))
        (= opcao "2") (do
                        (print "Digite o valor da venda: ") (flush)
                        (let [valor (Double/parseDouble (read-line))]
                          (swap! transacoes conj (criar-transacao "venda" valor)))
                        (println "Venda adicionada!")
                        (recur))
        (= opcao "3") (do
                        (println "Transações:")
                        (doseq [t @transacoes]
                          (println t))
                        (recur))
        (= opcao "4") (do
                        (println "Total de compras: " (calcula-valor (get-compras @transacoes)))
                        (recur))
        (= opcao "5") (do
                        (println "Total de vendas: " (calcula-valor (get-vendas @transacoes)))
                        (recur))
        (= opcao "0") (println "Saindo...")
        :else (do
                (println "Opção inválida!")
                (recur))))))

(defn -main [& args]
  (menu))