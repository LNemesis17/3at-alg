package at;

public class CustomMap<K,V> {
    private Entry<K, V>[] buckets;
    //Tamanho do bucket, por padrão é 16
    private static final int INITIAL_CAPACITY = 1 << 4;

    private int size = 0;

    public CustomMap() {
        this.buckets = new Entry[INITIAL_CAPACITY];
    }

    public void put(K key, V value) {
        //Cria a nova entry
        Entry<K, V> entry = new Entry<>(key, value, null);

        //Pega o bucket
        int bucket = key.hashCode() % INITIAL_CAPACITY;

        //Verifica se já existe
        Entry<K, V> existing = buckets[bucket];
        if (existing == null) {
            //Se não, adiciona ali e aumenta o size
            buckets[bucket] = entry;
            size++;
        } else {
            //Se sim, procura o ponto de inserção
            while (existing.next != null) {
                if (existing.key.equals(key)) {
                    existing.value = value;
                    return;
                }
                existing = existing.next;
            }

            if (existing.key.equals(key)) {
                existing.value = value;
            } else {
                existing.next = entry;
                size++;
            }
        }
    }

    public boolean containsKey(K key)
    {
        //Pega o bucket
        int bucket = key.hashCode() % INITIAL_CAPACITY;

        //Verifica se já existe
        Entry<K, V> existing = buckets[bucket];
        if (existing == null) {
            return false;
        } else {
            //Se eixste o bucket procura se o valor existe
            while (existing.next != null) {
                if (existing.key.equals(key)) {
                    return true;
                }
                existing = existing.next;
            }

            return existing.key.equals(key);
        }
    }

    public V get(K key) {
        //Procura o bucket
        Entry<K, V> bucket = buckets[key.hashCode() % INITIAL_CAPACITY];

        //Itera procurando o valor
        while (bucket != null) {
            if (bucket.key.equals(key)) {
                return bucket.value;
            }
            bucket = bucket.next;
        }
        return null;
    }

    public Entry<K, V>[] getBuckets() {
        return buckets;
    }
}