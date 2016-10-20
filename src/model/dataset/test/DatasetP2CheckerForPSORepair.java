package model.dataset.test;

import it.unimi.dsi.fastutil.ints.Int2IntLinkedOpenHashMap;
import java.util.Arrays;
import main.Main;
import model.dataset.DatasetP2Generator3;
import model.dataset.component.Lesson;
import model.dataset.component.Timeoff;
import model.dataset.core.Dataset2;
import model.dataset.core.DatasetConverter;
import model.dataset.core.WorkingSet;
import model.pso.component.ParticleP2;
import model.pso.component.Position;
import model.pso.component.Setting;
import model.pso.core.PSOP2;
import org.junit.Assert;
import org.junit.Test;

/*
 * This <Skripsi_003> project in package <model.dataset.test> created by : 
 * Name         : syafiq
 * Date / Time  : 12 October 2016, 2:52 AM.
 * Email        : syafiq.rezpector@gmail.com
 * Github       : syafiqq
 */
public class DatasetP2CheckerForPSORepair
{
    final Setting setting = Setting.getInstance();
    private Dataset2<Timeoff, Lesson>                  dataset;
    private WorkingSet                                 workingset;
    private DatasetConverter<Int2IntLinkedOpenHashMap> encoder;
    private DatasetConverter<Int2IntLinkedOpenHashMap> decoder;
    private DatasetP2Generator3                        gen;
    private PSOP2                                      pso;

    @SuppressWarnings("Duplicates") public void generateDataset()
    {
        Main.getMyDatabaseAccount();
        this.dataset = new Dataset2<>(1);
        this.workingset = new WorkingSet();
        this.encoder = new DatasetConverter<>();
        this.decoder = new DatasetConverter<>();
        this.gen = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();
    }

    @SuppressWarnings("Duplicates") @Test public void CheckSKS()
    {
        Main.getMyDatabaseAccount();
        this.dataset = new Dataset2<>(1);
        this.workingset = new WorkingSet();
        this.encoder = new DatasetConverter<>();
        this.decoder = new DatasetConverter<>();
        this.gen = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        Position[] positions;
/*
        positions[0] = new Position(new int[] {690, 689, 648, 583, 549, 651, 645, 0, 586, 547, 546, 650, 653, 585, 647, 0, 550, 548, 649, 646, 691, 584, 0, 652, 0, 587, 0, 686, 0, 0, 688, 644, 687, 0, 0, 0, 0, 0, 0, 0, 0, 0, 588, 0, 0, 0, 0});
        positions[1] = new Position(new int[] {684, 580, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 581, 579, 685, 0, 582, 683, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 682, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        positions[2] = new Position(new int[] {250, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 257, 252, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 256, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 258, 253, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 259, 251, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        positions[3] = new Position(new int[] {304, 307, 306, 308, 79, 23, 12, 0, 310, 82, 313, 300, 302, 314, 305, 0, 73, 10, 312, 3, 309, 301, 303, 0, 26, 13, 71, 75, 19, 70, 81, 0, 9, 5, 24, 14, 74, 4, 311, 17, 11, 7, 20, 8, 21, 80, 0, 18, 83, 27, 1, 15, 25, 2, 0, 77, 6, 78, 72, 22, 0, 0, 0, 0, 0, 0, 0, 84, 0, 0, 76, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16});
        positions[4] = new Position(new int[] {97, 120, 638, 566, 555, 210, 60, 438, 386, 65, 327, 553, 222, 0, 110, 223, 185, 87, 293, 665, 328, 41, 617, 207, 0, 225, 489, 348, 476, 0, 396, 561, 0, 517, 468, 0, 632, 163, 567, 363, 434, 122, 466, 280, 625, 639, 382, 0, 395, 50, 297, 412, 98, 428, 294, 296, 274, 453, 409, 140, 0, 427, 260, 341, 558, 0, 64, 661, 90, 282, 334, 345, 271, 91, 543, 445, 0, 170, 184, 658, 211, 0, 329, 284, 291, 654, 0, 450, 452, 568, 275, 413, 178, 670, 604, 149, 173, 332, 662, 339, 0, 151, 355, 574, 213, 123, 358, 441, 0, 448, 119, 51, 491, 0, 668, 551, 0, 276, 264, 349, 394, 0, 62, 104, 405, 673, 0, 609, 419, 0, 672, 356, 677, 117, 208, 263, 449, 433, 393, 383, 641, 482, 212, 416, 506, 0, 443, 655, 0, 423, 158, 230, 106, 559, 564, 542, 362, 440, 603, 417, 0, 86, 262, 505, 226, 219, 96, 477, 379, 331, 99, 164, 391, 0, 283, 607, 0, 565, 52, 0, 146, 102, 0, 347, 40, 451, 169, 455, 518, 261, 392, 414, 227, 509, 168, 0, 160, 475, 0, 39, 520, 0, 344, 112, 0, 105, 384, 446, 55, 664, 326, 0, 408, 150, 292, 95, 232, 521, 0, 385, 534, 0, 101, 676, 675, 526, 100, 134, 167, 342, 142, 357, 165, 594, 350, 218, 435, 68, 58, 172, 61, 180, 144, 159, 325, 0, 560, 0, 418, 63, 107, 524, 0, 454, 249, 485, 444, 380, 674, 0, 372, 233, 628, 92, 0, 266, 410, 643, 0, 575, 527, 656, 563, 0, 366, 556, 613, 94, 0, 388, 401, 36, 402, 471, 221, 368, 397, 59, 381, 116, 680, 131, 0, 614, 162, 343, 130, 510, 361, 0, 624, 633, 193, 375, 333, 138, 481, 369, 69, 93, 415, 335, 484, 272, 111, 642, 0, 436, 598, 0, 176, 608, 663, 615, 406, 290, 121, 316, 154, 183, 354, 127, 370, 148, 483, 152, 390, 462, 246, 619, 371, 0, 235, 89, 618, 0, 166, 67, 525, 0, 532, 188, 597, 620, 346, 0, 147, 0, 198, 141, 196, 467, 315, 56, 336, 191, 442, 360, 569, 0, 28, 295, 426, 602, 0, 497, 0, 389, 590, 0, 407, 459, 38, 217, 367, 616, 460, 0, 108, 220, 403, 621, 636, 145, 486, 231, 194, 637, 179, 0, 492, 0, 592, 557, 640, 340, 33, 516, 0, 374, 153, 500, 118, 0, 267, 461, 124, 627, 234, 0, 562, 0, 469, 522, 0, 273, 128, 45, 279, 201, 400, 114, 137, 398, 321, 660, 537, 186, 630, 0, 540, 495, 545, 88, 143, 0, 66, 265, 126, 571, 511, 544, 463, 519, 281, 0, 181, 420, 330, 623, 228, 554, 535, 589, 32, 182, 247, 404, 115, 161, 0, 671, 499, 515, 132, 373, 320, 209, 359, 0, 113, 669, 0, 657, 387, 0, 596, 429, 0, 200, 125, 0, 135, 0, 47, 667, 0, 156, 508, 0, 634, 0, 626, 473, 0, 139, 0, 478, 552, 0, 378, 48, 595, 0, 573, 287, 0, 502, 0, 528, 0, 44, 474, 298, 480, 376, 601, 0, 277, 0, 0, 299, 174, 0, 190, 439, 0, 0, 0, 133, 0, 629, 322, 0, 425, 611, 0, 54, 538, 365, 57, 289, 487, 432, 318, 0, 337, 0, 0, 189, 503, 498, 240, 278, 541, 85, 202, 0, 237, 42, 37, 681, 177, 0, 578, 0, 421, 422, 224, 430, 631, 0, 678, 286, 0, 447, 529, 0, 570, 0, 192, 206, 0, 531, 431, 0, 679, 0, 53, 205, 215, 523, 0, 610, 0, 46, 199, 0, 599, 530, 493, 244, 351, 424, 353, 572, 0, 236, 195, 319, 0, 635, 29, 0, 239, 338, 612, 0, 472, 187, 0, 605, 0, 496, 539, 0, 504, 470, 0, 501, 0, 666, 323, 0, 576, 216, 0, 197, 0, 288, 437, 317, 155, 606, 0, 0, 248, 533, 242, 204, 0, 0, 591, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 577, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 494, 0, 0, 0, 0, 0, 241, 0, 0, 0, 30, 507, 0, 0, 0, 0, 0, 0, 0, 600, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 229, 0, 0, 0, 411, 0, 0, 593, 0, 0, 0, 0, 0, 0, 0, 0, 103, 0, 0, 0, 0, 0, 0, 622, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 512, 0, 0, 0, 0, 0, 513, 0, 0, 0, 0, 0, 0, 488, 0, 0, 0, 0, 0, 0, 203, 0, 0, 0, 0, 0, 0, 536, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 399, 0, 0, 0, 514, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 659, 0, 0, 0, 0, 0, 0, 0, 0, 0, 31, 490, 0, 175, 0, 0, 0, 0, 0, 0, 0, 377, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 457, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 456, 0, 0, 0, 136, 0, 0, 0, 0, 34, 324, 157, 0, 0, 0, 171, 0, 0, 269, 0, 0, 0, 465, 0, 0, 0, 0, 49, 0, 0, 0, 0, 0, 0, 0, 0, 479, 0, 0, 0, 458, 0, 268, 0, 0, 0, 0, 0, 43, 0, 0, 0, 0, 214, 0, 0, 35, 464, 0, 0, 0, 238, 364, 270, 0, 0, 285, 0, 109, 243, 352, 0, 0, 0, 0, 0, 129, 0, 0, 0, 245});
*/
/*
        positions[0] = new Position(new int[] {690, 689, 648, 583, 549, 651, 645, 0, 586, 547, 546, 650, 653, 585, 647, 0, 550, 548, 649, 646, 691, 584, 0, 652, 0, 587, 0, 686, 0, 0, 688, 644, 687, 0, 0, 0, 0, 0, 0, 0, 0, 0, 588, 0, 0, 0, 0});
        positions[1] = new Position(new int[] {684, 580, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 581, 579, 685, 0, 582, 683, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 682, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        positions[2] = new Position(new int[] {250, 254, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 257, 252, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 256, 255, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 258, 253, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 259, 251, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0});
        positions[3] = new Position(new int[] {304, 307, 306, 308, 79, 23, 12, 0, 310, 82, 313, 300, 302, 314, 305, 0, 73, 10, 312, 3, 309, 301, 303, 0, 26, 13, 71, 75, 19, 70, 81, 0, 9, 5, 0, 14, 74, 4, 311, 0, 11, 7, 20, 8, 21, 80, 24, 0, 83, 27, 1, 15, 25, 2, 17, 0, 6, 78, 72, 22, 18, 77, 0, 0, 0, 0, 0, 84, 0, 0, 76, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 16});
        positions[4] = new Position(new int[] {97, 120, 638, 566, 555, 210, 60, 438, 386, 65, 327, 553, 222, 0, 110, 223, 185, 87, 293, 665, 328, 41, 617, 207, 0, 225, 489, 348, 476, 0, 396, 561, 0, 517, 468, 0, 632, 163, 567, 363, 434, 122, 466, 280, 625, 639, 382, 0, 395, 50, 297, 412, 98, 428, 294, 296, 274, 453, 409, 140, 0, 427, 260, 341, 558, 0, 64, 661, 90, 282, 334, 345, 271, 91, 543, 445, 0, 170, 184, 658, 211, 0, 329, 284, 291, 654, 0, 450, 452, 568, 275, 413, 178, 670, 604, 149, 173, 332, 662, 339, 0, 151, 355, 574, 213, 123, 358, 441, 0, 448, 119, 51, 491, 0, 668, 551, 0, 276, 264, 349, 394, 0, 62, 104, 405, 673, 0, 609, 419, 0, 672, 356, 677, 117, 208, 263, 449, 433, 393, 383, 641, 482, 212, 416, 506, 0, 443, 655, 0, 423, 158, 230, 106, 559, 564, 542, 362, 440, 603, 417, 0, 86, 262, 505, 226, 219, 96, 477, 379, 331, 99, 164, 391, 0, 283, 607, 0, 565, 52, 0, 146, 102, 0, 347, 40, 451, 169, 455, 518, 261, 392, 414, 227, 509, 168, 0, 160, 475, 0, 39, 520, 0, 344, 112, 0, 105, 384, 446, 55, 664, 326, 0, 408, 150, 292, 95, 232, 521, 0, 385, 534, 0, 101, 676, 675, 526, 100, 134, 167, 342, 142, 357, 165, 594, 350, 218, 435, 68, 58, 172, 61, 180, 144, 159, 325, 0, 560, 0, 418, 63, 107, 524, 0, 454, 249, 485, 444, 380, 674, 0, 372, 233, 628, 92, 0, 266, 410, 643, 0, 575, 527, 656, 563, 0, 366, 556, 613, 94, 0, 388, 401, 36, 402, 471, 221, 368, 397, 59, 381, 116, 680, 131, 0, 614, 162, 343, 130, 510, 361, 0, 624, 633, 193, 375, 333, 138, 481, 369, 69, 93, 415, 335, 484, 272, 111, 642, 0, 436, 598, 0, 176, 608, 663, 615, 406, 290, 121, 316, 154, 183, 354, 127, 370, 148, 483, 152, 390, 462, 246, 619, 371, 0, 235, 89, 618, 0, 166, 67, 525, 0, 532, 188, 597, 620, 346, 0, 147, 0, 198, 141, 196, 467, 315, 56, 336, 191, 442, 360, 569, 0, 28, 295, 426, 602, 0, 497, 0, 389, 590, 0, 407, 459, 38, 217, 367, 616, 460, 0, 108, 220, 403, 621, 636, 145, 486, 231, 194, 637, 179, 0, 492, 0, 592, 557, 640, 340, 33, 516, 0, 374, 153, 500, 118, 0, 267, 461, 124, 627, 234, 0, 562, 0, 469, 522, 0, 273, 128, 45, 279, 201, 400, 114, 137, 398, 321, 660, 537, 186, 630, 0, 540, 495, 545, 88, 143, 0, 66, 265, 126, 571, 511, 544, 463, 519, 281, 0, 181, 420, 330, 623, 228, 554, 535, 589, 32, 182, 247, 404, 115, 161, 0, 671, 499, 515, 132, 373, 320, 209, 359, 0, 113, 669, 0, 657, 387, 0, 596, 429, 0, 200, 125, 0, 135, 0, 47, 667, 0, 156, 508, 0, 634, 0, 626, 473, 0, 139, 0, 478, 552, 0, 378, 48, 595, 0, 573, 287, 0, 502, 0, 528, 0, 44, 474, 298, 480, 376, 601, 0, 277, 0, 0, 299, 174, 0, 190, 439, 0, 0, 0, 133, 0, 629, 322, 0, 425, 611, 0, 54, 538, 365, 57, 289, 487, 432, 318, 0, 337, 0, 0, 189, 503, 498, 240, 278, 541, 85, 202, 0, 237, 42, 37, 681, 177, 0, 578, 0, 421, 422, 224, 430, 631, 0, 678, 286, 0, 447, 529, 0, 570, 0, 192, 206, 0, 531, 431, 0, 679, 0, 53, 205, 215, 523, 0, 610, 0, 46, 199, 0, 599, 530, 493, 244, 351, 424, 353, 572, 0, 236, 195, 319, 0, 635, 29, 0, 239, 338, 612, 0, 472, 187, 0, 605, 0, 496, 539, 0, 504, 470, 0, 501, 0, 666, 323, 0, 576, 216, 0, 197, 0, 288, 437, 317, 155, 606, 0, 0, 248, 533, 242, 204, 0, 0, 591, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 577, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 494, 0, 0, 0, 0, 0, 241, 0, 0, 0, 30, 507, 0, 0, 0, 0, 0, 0, 0, 600, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 229, 0, 0, 0, 411, 0, 0, 593, 0, 0, 0, 0, 0, 0, 0, 0, 103, 0, 0, 0, 0, 0, 0, 622, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 512, 0, 0, 0, 0, 0, 513, 0, 0, 0, 0, 0, 0, 488, 0, 0, 0, 0, 0, 0, 203, 0, 0, 0, 0, 0, 0, 536, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 399, 0, 0, 0, 514, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 659, 0, 0, 0, 0, 0, 0, 0, 0, 0, 31, 490, 0, 175, 0, 0, 0, 0, 0, 0, 0, 377, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 457, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 456, 0, 0, 0, 136, 0, 0, 0, 0, 34, 324, 157, 0, 0, 0, 171, 0, 0, 269, 0, 0, 0, 465, 0, 0, 0, 0, 49, 0, 0, 0, 0, 0, 0, 0, 0, 479, 0, 0, 0, 458, 0, 268, 0, 0, 0, 0, 0, 43, 0, 0, 0, 0, 214, 0, 0, 35, 464, 0, 0, 0, 238, 364, 270, 0, 0, 285, 0, 109, 243, 352, 0, 0, 0, 0, 0, 129, 0, 0, 0, 245});
*/

        String data = "Position [0] : [651, 652, 688, 649, 689, 691, 548, 0, 0, 647, 550, 549, 583, 588, 584, 587, 586, 0, 686, 653, 650, 690, 644, 585, 0, 0, 0, 648, 0, 0, 0, 0, 687, 0, 546, 0, 646, 0, 0, 0, 0, 547, 0, 645, 0, 0, 0]\n" +
                "\t\tPosition [1] : [579, 580, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 581, 682, 684, 582, 683, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 685, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]\n" +
                "\t\tPosition [2] : [252, 258, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 257, 259, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 254, 256, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 253, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 251, 250, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]\n" +
                "\t\tPosition [3] : [3, 10, 300, 23, 24, 8, 27, 0, 313, 301, 311, 307, 302, 309, 310, 0, 7, 303, 314, 312, 304, 308, 81, 0, 9, 72, 71, 78, 74, 305, 14, 0, 21, 25, 0, 5, 12, 20, 70, 0, 1, 82, 13, 16, 2, 79, 17, 0, 84, 83, 4, 19, 80, 75, 306, 0, 26, 18, 0, 0, 77, 11, 0, 0, 0, 15, 73, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 76, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 22]\n" +
                "\t\tPosition [4] : [348, 654, 674, 401, 669, 589, 640, 169, 296, 422, 330, 41, 468, 519, 67, 346, 223, 102, 535, 378, 0, 489, 568, 134, 224, 65, 387, 389, 183, 101, 619, 0, 50, 615, 0, 271, 141, 0, 655, 132, 0, 418, 423, 227, 60, 64, 659, 447, 151, 414, 280, 406, 185, 520, 540, 342, 560, 395, 231, 293, 375, 638, 526, 343, 543, 432, 139, 657, 112, 145, 281, 509, 404, 63, 384, 90, 260, 133, 405, 334, 42, 146, 0, 88, 371, 184, 226, 361, 157, 120, 234, 149, 617, 86, 56, 325, 370, 121, 608, 93, 333, 572, 68, 397, 443, 416, 229, 564, 670, 556, 105, 475, 172, 452, 388, 335, 633, 85, 154, 130, 672, 57, 413, 544, 218, 448, 372, 661, 665, 641, 403, 100, 140, 614, 574, 166, 110, 435, 262, 446, 677, 119, 377, 123, 427, 365, 161, 476, 609, 411, 563, 440, 412, 374, 55, 118, 517, 355, 484, 173, 181, 94, 358, 219, 368, 165, 0, 138, 479, 212, 450, 490, 345, 603, 0, 340, 506, 152, 521, 61, 0, 363, 211, 359, 98, 438, 511, 0, 89, 571, 264, 360, 410, 225, 350, 263, 329, 467, 656, 0, 117, 658, 99, 566, 559, 673, 272, 552, 663, 675, 232, 483, 441, 142, 622, 107, 618, 182, 209, 109, 471, 394, 639, 52, 439, 125, 249, 299, 236, 660, 607, 292, 465, 241, 321, 186, 382, 537, 347, 0, 631, 126, 210, 430, 163, 545, 0, 167, 527, 0, 235, 49, 664, 561, 357, 59, 400, 597, 0, 667, 122, 170, 429, 396, 206, 113, 283, 555, 275, 0, 635, 390, 261, 364, 569, 482, 516, 491, 366, 668, 291, 681, 284, 454, 315, 168, 164, 0, 106, 108, 488, 513, 242, 433, 0, 213, 0, 0, 610, 385, 217, 0, 598, 0, 216, 379, 285, 324, 620, 0, 472, 111, 92, 158, 453, 276, 457, 277, 464, 156, 541, 0, 0, 131, 449, 434, 221, 0, 179, 246, 0, 409, 676, 0, 174, 0, 590, 0, 356, 326, 634, 373, 0, 220, 567, 0, 147, 398, 376, 297, 624, 0, 0, 0, 66, 0, 415, 0, 0, 202, 0, 671, 595, 538, 205, 0, 114, 243, 331, 602, 0, 91, 195, 0, 417, 613, 0, 31, 287, 407, 279, 322, 269, 0, 636, 599, 0, 207, 426, 0, 204, 0, 193, 529, 557, 523, 486, 148, 565, 336, 0, 383, 0, 162, 601, 0, 87, 0, 408, 551, 143, 0, 500, 0, 419, 320, 298, 578, 0, 0, 0, 0, 349, 0, 62, 0, 386, 0, 192, 0, 637, 286, 0, 104, 197, 0, 0, 531, 680, 522, 294, 380, 362, 0, 497, 436, 0, 562, 0, 0, 530, 0, 0, 478, 69, 629, 600, 558, 273, 222, 444, 288, 473, 339, 189, 0, 237, 155, 616, 328, 554, 0, 58, 510, 503, 628, 442, 480, 0, 127, 290, 178, 0, 461, 159, 505, 508, 459, 144, 0, 534, 0, 575, 39, 0, 525, 451, 0, 0, 354, 524, 399, 316, 421, 632, 621, 518, 604, 504, 662, 44, 28, 230, 208, 233, 577, 0, 353, 327, 175, 43, 177, 0, 533, 95, 0, 344, 137, 0, 570, 0, 199, 203, 0, 393, 200, 0, 188, 45, 466, 124, 445, 116, 0, 611, 0, 0, 265, 187, 0, 194, 35, 553, 0, 115, 536, 0, 96, 0, 528, 228, 0, 282, 191, 0, 239, 267, 679, 240, 245, 420, 190, 135, 0, 477, 341, 507, 0, 153, 424, 40, 198, 0, 498, 0, 428, 317, 32, 492, 0, 0, 0, 0, 431, 606, 0, 591, 171, 0, 392, 462, 593, 0, 0, 0, 0, 0, 0, 0, 180, 97, 0, 0, 0, 437, 274, 0, 0, 0, 318, 514, 0, 0, 0, 381, 0, 627, 0, 0, 0, 0, 0, 666, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 456, 0, 612, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 238, 332, 0, 0, 0, 0, 532, 573, 0, 642, 0, 0, 0, 0, 496, 0, 160, 338, 150, 0, 319, 0, 0, 0, 0, 0, 36, 463, 214, 0, 0, 0, 176, 30, 247, 29, 0, 0, 0, 295, 0, 0, 0, 0, 0, 0, 455, 0, 201, 53, 0, 0, 0, 0, 0, 0, 51, 474, 0, 0, 0, 501, 512, 0, 0, 0, 0, 0, 0, 0, 402, 0, 0, 0, 0, 0, 0, 592, 0, 0, 0, 0, 0, 494, 391, 0, 0, 0, 0, 0, 539, 0, 0, 0, 0, 0, 605, 268, 0, 196, 103, 0, 630, 0, 542, 0, 0, 0, 0, 460, 458, 0, 0, 0, 33, 495, 129, 469, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 351, 0, 0, 0, 0, 481, 0, 0, 0, 0, 0, 487, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 367, 0, 0, 0, 0, 0, 0, 215, 0, 485, 0, 626, 425, 0, 0, 0, 596, 369, 0, 0, 0, 48, 0, 0, 0, 352, 270, 0, 0, 0, 0, 0, 0, 493, 37, 0, 0, 0, 323, 0, 46, 0, 625, 0, 0, 0, 0, 0, 0, 0, 0, 0, 289, 0, 0, 0, 0, 594, 0, 0, 0, 0, 623, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 278, 0, 337, 499, 0, 515, 0, 0, 0, 128, 0, 136, 576, 502, 0, 0, 0, 0, 248, 678, 0, 266, 0, 0, 0, 0, 0, 470, 0, 54, 34, 0, 0, 0, 0, 0, 244, 643, 0, 47, 38]\n" +
                "\t\t";

        positions = this.retrievePositionFromRaw_001(data);
        this.setting.bglob_min = 0.4;
        this.setting.bglob_max = 0.6;
        this.setting.bloc_min = 0.7;
        this.setting.bloc_max = 0.9;
        this.setting.brand_min = 0.001;
        this.setting.brand_max = 0.01;
        this.setting.total_core = 3;
        this.setting.max_particle = 10;
        this.setting.max_epoch = 5000;

        this.pso = new PSOP2(this.setting, this.gen);
        this.pso.initializeSwarm();
        this.pso.updateSwarmFitness();
        final ParticleP2 suspect    = pso.particles[0];
        final Position[] suspectPos = suspect.data.positions;
        for(int i = -1, is = suspectPos.length; ++i < is; )
        {
            Position.replace(suspectPos[i], positions[i]);
        }

        this.printParticleComponent(suspect, 3);
        this.printAvailableClassroom(suspect, 3);
        this.pso.calculateFitness(suspect);
        System.out.println(suspect.data.fitness);

        this.pso.repairData(suspect);

        this.printParticleComponent(suspect, 3);
        this.printAvailableClassroom(suspect, 3);
        this.pso.calculateFitness(suspect);
        System.out.println(suspect.data.fitness);

        this.pso.repairData(suspect);

        this.printParticleComponent(suspect, 3);
        this.pso.calculateFitness(suspect);
        System.out.println(suspect.data.fitness);

        this.pso.repairData(suspect);

        this.printParticleComponent(suspect, 3);
        this.pso.calculateFitness(suspect);
        System.out.println(suspect.data.fitness);

        this.pso.repairData(suspect);

        this.printParticleComponent(suspect, 3);
        this.pso.calculateFitness(suspect);
        System.out.println(suspect.data.fitness);

        this.pso.repairData(suspect);

        this.printParticleComponent(suspect, 3);
        this.pso.calculateFitness(suspect);
        System.out.println(suspect.data.fitness);
    }

    private Position[] retrievePositionFromRaw_001(String data)
    {
        data = data.replace('\t', '\0');
        String     rawPosition[] = data.split("\n");
        Position[] positions     = new Position[this.workingset.lesson_pool.length];
        for(int i = -1, is = positions.length; ++i < is; )
        {
            positions[i] = new Position(Arrays.stream(rawPosition[i].split(":")[1].replace('[', '\0').replace(']', '\0').trim().split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray());
        }
        return positions;
    }


    private void printAvailableClassroom(ParticleP2 suspect, int index)
    {
        for(final int lesson : suspect.data.positions[index].position)
        {
            for(int i = -1, is = this.dataset.lessons[lesson].sks; ++i < is; )
            {
                System.out.printf("%s\t", Arrays.toString(this.dataset.lessons[lesson].available_classroom));
            }
        }
        System.out.println();
    }

    private void printParticleComponent(ParticleP2 suspect, int index)
    {
        for(final int classroom : this.workingset.lesson_pool[index].classrooms)
        {
            for(final int day : this.dataset.active_days)
            {
                System.out.print(Arrays.toString(this.workingset.lesson_pool[index].classroom_available_time[classroom][day]) + "\t");
            }
            System.out.println();
        }
        System.out.println();
        for(final int lesson : suspect.data.positions[index].position)
        {
            for(int i = -1, is = this.dataset.lessons[lesson].sks; ++i < is; )
            {
                System.out.printf("%d\t", lesson);
            }
        }
        System.out.println();
    }

    @Test public void beginTest()
    {
        Main.getMyDatabaseAccount();
        this.dataset = new Dataset2<>(1);
        this.workingset = new WorkingSet();
        this.encoder = new DatasetConverter<>();
        this.decoder = new DatasetConverter<>();
        this.gen = new DatasetP2Generator3(dataset, workingset, encoder, decoder);
        gen.generateDataset();

        String data = "Position [0] : [651, 652, 688, 649, 689, 691, 548, 0, 0, 647, 550, 549, 583, 588, 584, 587, 586, 0, 686, 653, 650, 690, 644, 585, 0, 0, 0, 648, 0, 0, 0, 0, 687, 0, 546, 0, 646, 0, 0, 0, 0, 547, 0, 645, 0, 0, 0]\n" +
                "\t\tPosition [1] : [579, 580, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 581, 682, 684, 582, 683, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 685, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]\n" +
                "\t\tPosition [2] : [252, 258, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 257, 259, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 254, 256, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 255, 253, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 251, 250, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]\n" +
                "\t\tPosition [3] : [3, 10, 300, 23, 24, 8, 27, 0, 313, 301, 311, 307, 302, 309, 310, 0, 7, 303, 314, 312, 304, 308, 81, 0, 9, 72, 71, 78, 74, 305, 14, 0, 21, 25, 0, 5, 12, 20, 70, 0, 1, 82, 13, 16, 2, 79, 17, 0, 84, 83, 4, 19, 80, 75, 306, 0, 26, 18, 0, 0, 77, 11, 0, 0, 0, 15, 73, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 76, 0, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0, 22]\n" +
                "\t\tPosition [4] : [348, 654, 674, 401, 669, 589, 640, 169, 296, 422, 330, 41, 468, 519, 67, 346, 223, 102, 535, 378, 0, 489, 568, 134, 224, 65, 387, 389, 183, 101, 619, 0, 50, 615, 0, 271, 141, 0, 655, 132, 0, 418, 423, 227, 60, 64, 659, 447, 151, 414, 280, 406, 185, 520, 540, 342, 560, 395, 231, 293, 375, 638, 526, 343, 543, 432, 139, 657, 112, 145, 281, 509, 404, 63, 384, 90, 260, 133, 405, 334, 42, 146, 0, 88, 371, 184, 226, 361, 157, 120, 234, 149, 617, 86, 56, 325, 370, 121, 608, 93, 333, 572, 68, 397, 443, 416, 229, 564, 670, 556, 105, 475, 172, 452, 388, 335, 633, 85, 154, 130, 672, 57, 413, 544, 218, 448, 372, 661, 665, 641, 403, 100, 140, 614, 574, 166, 110, 435, 262, 446, 677, 119, 377, 123, 427, 365, 161, 476, 609, 411, 563, 440, 412, 374, 55, 118, 517, 355, 484, 173, 181, 94, 358, 219, 368, 165, 0, 138, 479, 212, 450, 490, 345, 603, 0, 340, 506, 152, 521, 61, 0, 363, 211, 359, 98, 438, 511, 0, 89, 571, 264, 360, 410, 225, 350, 263, 329, 467, 656, 0, 117, 658, 99, 566, 559, 673, 272, 552, 663, 675, 232, 483, 441, 142, 622, 107, 618, 182, 209, 109, 471, 394, 639, 52, 439, 125, 249, 299, 236, 660, 607, 292, 465, 241, 321, 186, 382, 537, 347, 0, 631, 126, 210, 430, 163, 545, 0, 167, 527, 0, 235, 49, 664, 561, 357, 59, 400, 597, 0, 667, 122, 170, 429, 396, 206, 113, 283, 555, 275, 0, 635, 390, 261, 364, 569, 482, 516, 491, 366, 668, 291, 681, 284, 454, 315, 168, 164, 0, 106, 108, 488, 513, 242, 433, 0, 213, 0, 0, 610, 385, 217, 0, 598, 0, 216, 379, 285, 324, 620, 0, 472, 111, 92, 158, 453, 276, 457, 277, 464, 156, 541, 0, 0, 131, 449, 434, 221, 0, 179, 246, 0, 409, 676, 0, 174, 0, 590, 0, 356, 326, 634, 373, 0, 220, 567, 0, 147, 398, 376, 297, 624, 0, 0, 0, 66, 0, 415, 0, 0, 202, 0, 671, 595, 538, 205, 0, 114, 243, 331, 602, 0, 91, 195, 0, 417, 613, 0, 31, 287, 407, 279, 322, 269, 0, 636, 599, 0, 207, 426, 0, 204, 0, 193, 529, 557, 523, 486, 148, 565, 336, 0, 383, 0, 162, 601, 0, 87, 0, 408, 551, 143, 0, 500, 0, 419, 320, 298, 578, 0, 0, 0, 0, 349, 0, 62, 0, 386, 0, 192, 0, 637, 286, 0, 104, 197, 0, 0, 531, 680, 522, 294, 380, 362, 0, 497, 436, 0, 562, 0, 0, 530, 0, 0, 478, 69, 629, 600, 558, 273, 222, 444, 288, 473, 339, 189, 0, 237, 155, 616, 328, 554, 0, 58, 510, 503, 628, 442, 480, 0, 127, 290, 178, 0, 461, 159, 505, 508, 459, 144, 0, 534, 0, 575, 39, 0, 525, 451, 0, 0, 354, 524, 399, 316, 421, 632, 621, 518, 604, 504, 662, 44, 28, 230, 208, 233, 577, 0, 353, 327, 175, 43, 177, 0, 533, 95, 0, 344, 137, 0, 570, 0, 199, 203, 0, 393, 200, 0, 188, 45, 466, 124, 445, 116, 0, 611, 0, 0, 265, 187, 0, 194, 35, 553, 0, 115, 536, 0, 96, 0, 528, 228, 0, 282, 191, 0, 239, 267, 679, 240, 245, 420, 190, 135, 0, 477, 341, 507, 0, 153, 424, 40, 198, 0, 498, 0, 428, 317, 32, 492, 0, 0, 0, 0, 431, 606, 0, 591, 171, 0, 392, 462, 593, 0, 0, 0, 0, 0, 0, 0, 180, 97, 0, 0, 0, 437, 274, 0, 0, 0, 318, 514, 0, 0, 0, 381, 0, 627, 0, 0, 0, 0, 0, 666, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 456, 0, 612, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 238, 332, 0, 0, 0, 0, 532, 573, 0, 642, 0, 0, 0, 0, 496, 0, 160, 338, 150, 0, 319, 0, 0, 0, 0, 0, 36, 463, 214, 0, 0, 0, 176, 30, 247, 29, 0, 0, 0, 295, 0, 0, 0, 0, 0, 0, 455, 0, 201, 53, 0, 0, 0, 0, 0, 0, 51, 474, 0, 0, 0, 501, 512, 0, 0, 0, 0, 0, 0, 0, 402, 0, 0, 0, 0, 0, 0, 592, 0, 0, 0, 0, 0, 494, 391, 0, 0, 0, 0, 0, 539, 0, 0, 0, 0, 0, 605, 268, 0, 196, 103, 0, 630, 0, 542, 0, 0, 0, 0, 460, 458, 0, 0, 0, 33, 495, 129, 469, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 351, 0, 0, 0, 0, 481, 0, 0, 0, 0, 0, 487, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 367, 0, 0, 0, 0, 0, 0, 215, 0, 485, 0, 626, 425, 0, 0, 0, 596, 369, 0, 0, 0, 48, 0, 0, 0, 352, 270, 0, 0, 0, 0, 0, 0, 493, 37, 0, 0, 0, 323, 0, 46, 0, 625, 0, 0, 0, 0, 0, 0, 0, 0, 0, 289, 0, 0, 0, 0, 594, 0, 0, 0, 0, 623, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 278, 0, 337, 499, 0, 515, 0, 0, 0, 128, 0, 136, 576, 502, 0, 0, 0, 0, 248, 678, 0, 266, 0, 0, 0, 0, 0, 470, 0, 54, 34, 0, 0, 0, 0, 0, 244, 643, 0, 47, 38]\n" +
                "\t\t";

        Position[] positions = this.retrievePositionFromRaw_001(data);
        this.setting.bglob_min = 0.4;
        this.setting.bglob_max = 0.6;
        this.setting.bloc_min = 0.7;
        this.setting.bloc_max = 0.9;
        this.setting.brand_min = 0.001;
        this.setting.brand_max = 0.01;
        this.setting.total_core = 3;
        this.setting.max_particle = 10;
        this.setting.max_epoch = 5000;

        this.pso = new PSOP2(this.setting, this.gen);
        //this.pso.initializeLogger();
        this.pso.initializeSwarm();
        this.pso.updateSwarmFitness();
        final ParticleP2 suspect    = pso.particles[0];
        final Position[] suspectPos = suspect.data.positions;
        for(int i = -1, is = suspectPos.length; ++i < is; )
        {
            Position.replace(suspectPos[i], positions[i]);
        }

        this.pso.calculateFitness(suspect);
        System.out.println(suspect.data.fitness);
        this.pso.repairData(suspect);
        this.pso.calculateFitness(suspect);
        System.out.println(suspect.data.fitness);
        this.pso.repairData(suspect);
        this.pso.calculateFitness(suspect);
        System.out.println(suspect.data.fitness);
    }

    @Test public void testRepairWithAutomation()
    {
        this.setting.bglob_min = 0.4;
        this.setting.bglob_max = 0.6;
        this.setting.bloc_min = 0.7;
        this.setting.bloc_max = 0.9;
        this.setting.brand_min = 0.001;
        this.setting.brand_max = 0.01;
        this.setting.total_core = 3;
        this.setting.max_particle = 10;
        this.setting.max_epoch = 5000;

        this.generateDataset();
        this.pso = new PSOP2(this.setting, this.gen);
        pso.initializeSwarm();
        pso.updateSwarmFitness();
        final int[][]    cnt = new int[5][];
        final Position[] tp  = this.pso.particles[0].data.positions;
        for(int i = -1, is = tp.length; ++i < is; )
        {
            cnt[i] = new int[tp[i].position.length];
        }
        while(!pso.isConditionSatisfied())
        {
            for(int i = -1, is = this.pso.particles.length; ++i < is; )
            {
                final ParticleP2 particle = this.pso.particles[i];
                final Position[] ttp      = particle.data.positions;
                final Position[] ttpb     = particle.pBest.positions;
                final double     pB1      = particle.data.fitness;
                for(int j = -1, js = tp.length; ++j < js; )
                {
                    System.arraycopy(ttp[j].position, 0, cnt[j], 0, ttp[j].position.length);
                }
                particle.assignPBest();
                final double pB2 = particle.pBest.fitness;
                for(int j = -1, js = tp.length; ++j < js; )
                {
                    System.arraycopy(ttpb[j].position, 0, ttp[j].position, 0, ttpb[j].position.length);
                }
                this.pso.calculateFitness(particle);
                Assert.assertEquals(pB2, particle.data.fitness, 0);
                for(int j = -1, js = 2; ++j < js; )
                {
                    this.pso.repairData(particle);
                    this.pso.calculateFitness(particle);
                    Assert.assertEquals(pB2, particle.data.fitness, 0);
                }
                for(int j = -1, js = tp.length; ++j < js; )
                {
                    System.arraycopy(cnt[j], 0, ttp[j].position, 0, cnt[j].length);
                }
                this.pso.calculateFitness(particle);
                Assert.assertEquals(pB1, particle.data.fitness, 0);
            }


            final ParticleP2 ps   = this.pso.particles[0];
            final Position[] ttp  = ps.data.positions;
            final Position[] ttpb = this.pso.gBest.positions;
            final double     pB1  = ps.data.fitness;
            for(int j = -1, js = tp.length; ++j < js; )
            {
                System.arraycopy(ttp[j].position, 0, cnt[j], 0, ttp[j].position.length);
            }
            pso.assignGBest();
            final double pB2 = this.pso.gBest.fitness;
            for(int j = -1, js = tp.length; ++j < js; )
            {
                System.arraycopy(ttpb[j].position, 0, ttp[j].position, 0, ttpb[j].position.length);
            }
            this.pso.calculateFitness(ps);
            Assert.assertEquals(pB2, ps.data.fitness, 0);
            for(int j = -1, js = 2; ++j < js; )
            {
                this.pso.repairData(ps);
                this.pso.calculateFitness(ps);
                Assert.assertEquals(pB2, ps.data.fitness, 0);
            }
            for(int j = -1, js = tp.length; ++j < js; )
            {
                System.arraycopy(cnt[j], 0, ttp[j].position, 0, cnt[j].length);
            }
            this.pso.calculateFitness(ps);
            Assert.assertEquals(pB1, ps.data.fitness, 0);


            for(int i = -1, is = this.pso.particles.length; ++i < is; )
            {
                final ParticleP2 particle = this.pso.particles[i];
                particle.calculateVelocity(this.pso.gBest, this.pso.cEpoch, this.setting.max_epoch);
                particle.updateData();
                this.pso.repairData(particle);
                this.pso.calculateFitness(particle);
                final Position[] ttp1 = particle.data.positions;
                final double     pB11 = particle.data.fitness;
                for(int j = -1, js = tp.length; ++j < js; )
                {
                    System.arraycopy(ttp1[j].position, 0, cnt[j], 0, ttp1[j].position.length);
                }
                Assert.assertEquals(pB11, particle.data.fitness, 0);
                for(int j = -1, js = 2; ++j < js; )
                {
                    this.pso.repairData(particle);
                    this.pso.calculateFitness(particle);
                    Assert.assertEquals(pB11, particle.data.fitness, 0);
                }
                for(int j = -1, js = tp.length; ++j < js; )
                {
                    System.arraycopy(cnt[j], 0, ttp1[j].position, 0, cnt[j].length);
                }
                this.pso.calculateFitness(particle);
                Assert.assertEquals(pB11, particle.data.fitness, 0);
            }
            pso.updateStoppingCondition();
        }
    }
}
