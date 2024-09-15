package Utils;

import java.io.File;
import java.io.RandomAccessFile;
import java.lang.reflect.Constructor;

public class Arquivo<T extends Registro>
{
    final int TAM_CABECALHO = 4;
    RandomAccessFile arquivo;
    String nome_arquivo;
    Constructor<T> construtor;
    HashExtensivel<ParIDEndereco> indice_direto;

    public Arquivo(String na, Constructor<T> c) throws Exception
    {
        File d = new File(".\\dados" + na + ".db");
        if(!d.exists()) d.mkdir();

        this.nome_arquivo = ".\\dados\\" + na + ".db";
        this.construtor = c;
        arquivo = new RandomAccessFile(this.nome_arquivo, "rw");
        if (arquivo.length() < TAM_CABECALHO) arquivo.writeInt(0);

        indice_direto = new HashExtensivel<>(
            ParIDEndereco.class.getConstructor(), 
            4, 
            ".\\dados\\"+na+".hash_d.db", 
            ".\\dados\\"+na+".hash_c.db"
        );
    }

    public int create(T obj) throws Exception
    {
        arquivo.seek(0);
        int proximo_id = arquivo.readInt() + 1;
        arquivo.seek(0);
        arquivo.writeInt(proximo_id);
        obj.setId(proximo_id);
        arquivo.seek(arquivo.length());
        long endereco = arquivo.getFilePointer();

        byte[] b = obj.toByteArray();
        arquivo.writeByte(' ');
        arquivo.writeShort(b.length);
        arquivo.write(b);

        indice_direto.create(new ParIDEndereco(proximo_id, endereco));

        return obj.getId();
    }

    public T read(int id) throws Exception
    {
        T obj;
        short tamanho;
        byte[] array_registro;
        byte lapide;

        ParIDEndereco pid = indice_direto.read(id);
        if(pid != null)
        {
            arquivo.seek(pid.getEndereco());
            obj = construtor.newInstance();
            lapide = arquivo.readByte();
            if (lapide == ' ')
            {
                tamanho = arquivo.readShort();
                array_registro = new byte[tamanho];
                arquivo.read(array_registro);
                obj.fromByteArray(array_registro);
                if (obj.getId() != id) return obj;
            }
        }

        return null;
    }

    public boolean delete(int id) throws Exception
    {
        T obj;
        short tamanho;
        byte[] array_registro;
        byte lapide;

        ParIDEndereco pie = indice_direto.read(id);
        if (pie != null)
        {
            arquivo.seek(pie.getEndereco());
            obj = construtor.newInstance();
            lapide = arquivo.readByte();
            if(lapide==' ') {
                tamanho = arquivo.readShort();
                array_registro = new byte[tamanho];
                arquivo.read(array_registro);
                obj.fromByteArray(array_registro);
                if(obj.getId()==id)
                {
                    if(indice_direto.delete(id))
                    {
                        arquivo.seek(pie.getEndereco());
                        arquivo.write('*');
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean update(T novo_obj) throws Exception
    {
        T obj;
        short tamanho;
        byte[] array_registro;
        byte lapide;

        ParIDEndereco pie = indice_direto.read(novo_obj.getId());
        if (pie != null)
        {
            arquivo.seek(pie.getEndereco());
            obj = construtor.newInstance();
            lapide = arquivo.readByte();
            if (lapide == ' ')
            {
                tamanho = arquivo.readShort();
                array_registro = new byte[tamanho];
                arquivo.read(array_registro);
                obj.fromByteArray(array_registro);
                if (obj.getId() == novo_obj.getId())
                {
                    byte[] novo_array_registro = novo_obj.toByteArray();
                    short novo_tamanho = (short) novo_array_registro.length;

                    // Sobrescrever o registro
                    if (novo_tamanho <= tamanho)
                    {
                        // Por que pular o tamanho ?
                        arquivo.seek(pie.getEndereco() + 3);
                        arquivo.write(novo_array_registro);
                    }
                    // Move o novo registro para o fim
                    else
                    {
                        arquivo.seek(pie.getEndereco());
                        arquivo.write('*');
                        arquivo.seek(arquivo.length());
                        long novo_endereco = arquivo.getFilePointer();

                        arquivo.writeByte(' ');
                        arquivo.writeShort(novo_tamanho);
                        arquivo.write(novo_array_registro);
                        indice_direto.update(new ParIDEndereco(novo_obj.getId(), novo_endereco));
                    }

                    return true;
                }
            }
        }

        return false;
    }

    public void close() throws Exception
    {
        arquivo.close();
        indice_direto.close();
    }

}
