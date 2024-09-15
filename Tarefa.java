import Utils.Registro;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDate;

public class Tarefa implements Registro
{
    private int id;
    private String nome;
    private LocalDate data_criacao;
    private LocalDate data_conclusao;
    private String status;
    private short prioridade;

    public Tarefa()
    {
        this(-1, "", LocalDate.now(), LocalDate.now(), "", (short) 0);
    }

    public Tarefa(String n, LocalDate x, LocalDate y, String s, short p)
    {
        this(-1, n, x, y, s, p);
    }

    public Tarefa(int i, String n, LocalDate x, LocalDate y, String s, short p)
    {
        this.id = i;
        this.nome = n;
        this.data_criacao = x;
        this.data_conclusao = y;
        this.status = s;
        this.prioridade = p;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getId()
    {
        return this.id;
    }

    public byte[] toByteArray() throws IOException
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);

        dos.write(this.id);
        dos.writeUTF(this.nome);
        dos.writeInt((int) this.data_criacao.toEpochDay());
        dos.writeInt((int) this.data_conclusao.toEpochDay());
        dos.writeUTF(this.status);
        dos.writeShort(this.prioridade);

        return baos.toByteArray();
    }

    public void fromByteArray(byte[] b) throws IOException
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        DataInputStream dis = new DataInputStream(bais);

        this.id = dis.readInt();
        this.nome = dis.readUTF();
        this.data_criacao = LocalDate.ofEpochDay(dis.readInt());
        this.data_conclusao = LocalDate.ofEpochDay(dis.readInt());
        this.status = dis.readUTF();
        this.prioridade = dis.readShort();
    }

}
