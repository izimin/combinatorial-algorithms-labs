using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Huffman
{
    class HuffmanInfo
    {
        HuffmanTree Tree; // дерево кода Хаффмана, потребуется для распаковки
        Dictionary<char, string> Table; // словарь, хранящий коды всех символов, будет удобен для сжатия
        public HuffmanInfo(string fileName)
        {
            string line;
            var sr = new StreamReader(fileName, Encoding.Unicode);

            var lists = new List<HuffmanTree>();

            // считать информацию о частотах символов
            while ((line = sr.ReadLine()) != null)
            {
                if (line.Length == 0)
                {
                    //TODO: отдельная обработка строки, которой соответствует частота символа "конец строки" 
                    lists.Add(new HuffmanTree('\n', double.Parse(sr.ReadLine())));
                }
                else
                {
                    //TODO: создаем вершину (лист) дерева с частотой очередного символа
                    lists.Add(new HuffmanTree(line[0], double.Parse(line.Substring(1))));
                }
            }

            sr.Close();

            // TODO: добавить еще одну вершину-лист, соответствующую символу с кодом 0 ('\0'), который будет означать конец файла. Частота такого символа, очевидно, должна быть очень маленькой, т.к. такой символ встречается только 1 раз во всем файле (можно даже сделать частоту = 0)
            lists.Add(new HuffmanTree('\0', 0.0));

            // TODO: построить дерево кода Хаффмана путем последовательного объединения листьев

            lists.Sort((x, y) => x.freq.CompareTo(y.freq));

            while (lists.Count > 1)
            {
                var newNode = new HuffmanTree(lists[0], lists[1]);
                lists.RemoveRange(0, 2);

                var iMax = lists.FindIndex(x => x.freq > newNode.freq);
                lists.Insert(iMax > 0 ? iMax - 1 : lists.Count, newNode);
            }

            Tree = lists[0];
            Table = new Dictionary<char, string>();

            // TODO: заполнить таблицу кодирования Table на основе обхода построенного дерева
            Nlr(Tree, "");
        }

        public void Nlr(HuffmanTree tree, string code)
        {
            if (tree == null) return;
            if (tree.isTerminal)
                Table.Add(tree.ch, code);
            Nlr(tree.left, code + '0');
            Nlr(tree.rigth, code + '1');
        }

        public void Compress(string inpFile, string outFile)
        {
            var sr = new StreamReader(inpFile, Encoding.Unicode);
            var sw = new ArchWriter(outFile); //нужна побитовая запись, поэтому использовать StreamWriter напрямую нельзя
            string line;
            while ((line = sr.ReadLine()) != null)
            {
                // TODO: посимвольно обрабатываем строку, кодируем, пишем в sw
                foreach (var ch in line)
                {
                    sw.WriteWord(Table[ch]);
                }

                sw.WriteWord(Table['\n']);
            }

            sr.Close();
            sw.WriteWord(Table['\0']); // записываем признак конца файла
            sw.Finish();
        }

        public void Decompress(string archFile, string txtFile)
        {
            var sr = new ArchReader(archFile); // нужно побитовое чтение
            var sw = new StreamWriter(txtFile, false, Encoding.Unicode);
            var node = Tree; 
            while (sr.ReadBit(out var curBit))
            {
                // TODO: побитово (!) разбираем архив
                node = curBit == 0 ? node.left : node.rigth;

                if (node.isTerminal && node.ch == '\0') break;
                if (node.isTerminal)
                {
                    sw.Write(node.ch);
                    node = Tree;
                }
            }
            sr.Finish();
            sw.Close();
        }
    }

}
